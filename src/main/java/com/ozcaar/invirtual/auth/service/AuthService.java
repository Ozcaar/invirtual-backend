package com.ozcaar.invirtual.auth.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ozcaar.invirtual.auth.dto.LoginDTO;
import com.ozcaar.invirtual.auth.security.CustomUserDetails;
import com.ozcaar.invirtual.user.model.UserModel;
import com.ozcaar.invirtual.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil; 
    private final AuthenticationManager authenticationManager; 
    private final UserRepository userRepository;

    public String loginUser(LoginDTO dto) {
        // Authentication
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        // Set last login
        updateLastLogin(dto.getEmail());

        // Generate token
        String token = jwtUtil.generateToken(dto.getEmail());
        
        return token;
    }

    // public Optional<UserModel> getUserFromToken(String token) {
    //     String email = jwtUtil.extractUsername(token);
    //     return userRepository.findByEmail(email);
    // }

    public Optional<UserModel> getUserFromToken() {
        String email = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return userRepository.findByEmail(email);
    }

    public void updateLastLogin(String email) {
        Optional<UserModel> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            UserModel user = optionalUser.get();
            user.setLast_login(LocalDateTime.now());
            userRepository.save(user);
        }
    }

    // public String extractTokenFromRequest(HttpServletRequest request) {
    //     String authorizationHeader = request.getHeader("Authorization");
    //     if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
    //         return authorizationHeader.substring(7);
    //     }
    //     return null;
    // }
}
