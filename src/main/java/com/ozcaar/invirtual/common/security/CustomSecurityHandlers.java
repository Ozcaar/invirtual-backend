package com.ozcaar.invirtual.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozcaar.invirtual.common.exception.global.ApiError;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.Collections;

@Configuration
public class CustomSecurityHandlers {

    public static class CustomAccessDeniedHandler implements AccessDeniedHandler {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
                throws IOException, ServletException {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");

            ApiError error = new ApiError(
                HttpStatus.FORBIDDEN + " ACCESS_DENIED",
                "No tienes permiso para acceder a este recurso.",
                Collections.emptyList()
            );

            response.getWriter().write(objectMapper.writeValueAsString(error));
        }
    }

    public static class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                             org.springframework.security.core.AuthenticationException ex)
                throws IOException, ServletException {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");

            ApiError error = new ApiError(
                HttpStatus.UNAUTHORIZED + " UNAUTHORIZED",
                "No estás autenticado para acceder a este recurso.",
                Collections.emptyList()
            );

            response.getWriter().write(objectMapper.writeValueAsString(error));
        }
    }
}
