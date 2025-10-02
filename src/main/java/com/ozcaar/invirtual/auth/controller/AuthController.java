package com.ozcaar.invirtual.auth.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@Tag(name = "Auth Controller", description = "autenticar usuarios")
@RequiredArgsConstructor
public class AuthController implements AuthApiDoc {
    
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario", description = "", security = {})
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody UserCreateDTO dto) {
        Map<String, Object> response = authService.registerUser(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "", security = {})
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO dto) {
        String token = authService.loginUser(dto);
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.CREATED);
    }
}
