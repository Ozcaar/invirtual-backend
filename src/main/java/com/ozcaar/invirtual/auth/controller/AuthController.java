package com.ozcaar.invirtual.auth.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.ozcaar.invirtual.auth.dto.LoginDTO;
import com.ozcaar.invirtual.auth.service.AuthService;
import com.ozcaar.invirtual.common.exception.documentation.AuthApiDoc;
import com.ozcaar.invirtual.user.dto.create.UserCreateDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "autenticar usuarios")
@RequiredArgsConstructor
public class AuthController implements AuthApiDoc {

    private final AuthService authService;

    @Deprecated
    // @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario", description = "", security = {})
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody UserCreateDTO dto) {
        Map<String, Object> response = authService.registerUser(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Deprecated
    // @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "", security = {})
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO dto) {
        String token = authService.loginUser(dto);
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.CREATED);
    }

    @GetMapping("/api/debug/me")
    public Map<String, Object> debug(@AuthenticationPrincipal Jwt jwt, Authentication authentication) {
        if (jwt == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing JWT");
        }

        Object userMetadata = jwt.getClaim("user_metadata");
        // Object appMetadata = jwt.getClaim("app_metadata");

        return Map.of(
                // "sub", jwt.getSubject(),
                "email", jwt.getClaimAsString("email"),
                // "aud", jwt.getAudience(),
                // "iss", jwt.getIssuer() != null ? jwt.getIssuer().toString() : null,

                // supabase metadata
                "user_metadata", userMetadata,
                // "app_metadata", appMetadata,

                // real authorities (no claim)
                "authorities", authentication != null ? authentication.getAuthorities() : null

        // general inspection
        // "claims", jwt.getClaims()
        );
    }

}
