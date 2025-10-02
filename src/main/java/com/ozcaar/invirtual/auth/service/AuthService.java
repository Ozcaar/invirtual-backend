package com.ozcaar.invirtual.auth.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozcaar.invirtual.auth.dto.LoginDTO;
import com.ozcaar.invirtual.auth.security.CustomUserDetails;
import com.ozcaar.invirtual.common.exception.global.AlreadyExistsException;
import com.ozcaar.invirtual.user.dto.create.UserCreateDTO;
import com.ozcaar.invirtual.user.mapper.UserMapper;
import com.ozcaar.invirtual.user.model.UserModel;
import com.ozcaar.invirtual.user.repository.UserRepository;
import com.ozcaar.invirtual.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil; 
    private final AuthenticationManager authenticationManager; 
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Map<String, Object> registerUser(UserCreateDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new AlreadyExistsException("Ya existe un usuario con ese correo");
        }

        UserModel user = userMapper.toEntity(dto);

        // Encrypt password
        String hashedPassword = passwordEncoder.encode(dto.getPassword_plain());
        user.setPassword_hash(hashedPassword);

        // Active by default
        user.setActive(true);

        // First save the user to generate the ID
        UserModel savedUser = userRepository.save(user);
        if (savedUser.getUser_id() == null) {
            throw new RuntimeException("El usuario no se guardó correctamente. ID no generado.");
        }

        // Set default role
        userService.assignDefaultRole(savedUser);

        // Log user & get token
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(savedUser.getEmail());
        loginDTO.setPassword(dto.getPassword_plain());
        String token = loginUser(loginDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("user", userMapper.toDTO(savedUser));
        response.put("token", token);

        return response;
    }

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
