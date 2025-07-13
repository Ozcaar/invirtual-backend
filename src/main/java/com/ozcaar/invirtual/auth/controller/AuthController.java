package com.ozcaar.invirtual.auth.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ozcaar.invirtual.auth.dto.LoginDTO;
import com.ozcaar.invirtual.common.exception.documentation.AuthApiDoc;
import com.ozcaar.invirtual.user.dto.create.UserCreateDTO;
import com.ozcaar.invirtual.user.dto.read.UserReadDTO;
import com.ozcaar.invirtual.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Auth Controller", description = "autenticar usuarios")
public class AuthController implements AuthApiDoc {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario", description = "", security = {})
    public ResponseEntity<UserReadDTO> register(@Valid @RequestBody UserCreateDTO dto) {
        UserReadDTO user = userService.createUser(dto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "", security = {})
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO dto) {
        String token = userService.loginUser(dto);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
