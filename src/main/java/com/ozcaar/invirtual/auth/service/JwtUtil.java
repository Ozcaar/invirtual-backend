package com.ozcaar.invirtual.auth.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

// import com.ozcaar.invirtual.Models.UserModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    // public String generateToken(String email, String role) {
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                // .claim("role", user.getRole().getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1h
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey.getBytes())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);

            Date expiration = Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

            boolean isTokenExpired = expiration.before(new Date());

            // Validate if is expired
            return (username.equals(userDetails.getUsername()) && !isTokenExpired);
        } catch (Exception ex) {
            // Any error (invalid token, bad token, etc.) retunrs false
            return false;
        }
    }

    public String getEmailOfAuthenticatedUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public List<String> getRolesOfAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null) {
            // Get athorities from authenticated user
            List<String> roles = authentication.getAuthorities()
                                              .stream()
                                              .map(GrantedAuthority::getAuthority) // Get role name
                                              .collect(Collectors.toList());
            return roles;
        } else {
            throw new RuntimeException("El usuario no está autenticado.");
        }
    }

    public Boolean authenticatedUserUserIsAdminOrDev() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("No autenticado");
        }

        boolean isAdminOrDev = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .anyMatch(a -> a.equals("ROLE_ADMIN") || a.equals("ROLE_DEV"));            

        return isAdminOrDev;
    }
}
