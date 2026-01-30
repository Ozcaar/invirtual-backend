package com.ozcaar.invirtual.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozcaar.invirtual.common.exception.global.ApiError;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@Configuration
public class CustomSecurityHandlers {

    public static class CustomAccessDeniedHandler implements AccessDeniedHandler {

        private final ObjectMapper mapper;

        public CustomAccessDeniedHandler(ObjectMapper mapper) {
            this.mapper = mapper;
        }

        @Override
        public void handle(HttpServletRequest request,
                HttpServletResponse response,
                AccessDeniedException ex) throws IOException {

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");

            ApiError error = new ApiError(
                    403,
                    "FORBIDDEN",
                    "No tienes permiso para realizar esta acción");

            mapper.writeValue(response.getOutputStream(), error);
            response.flushBuffer();
        }
    }

    public static class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

        private final ObjectMapper mapper;

        public CustomAuthenticationEntryPoint(ObjectMapper mapper) {
            this.mapper = mapper;
        }

        @Override
        public void commence(HttpServletRequest request,
                HttpServletResponse response,
                AuthenticationException authException) throws IOException {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");

            ApiError error = new ApiError(
                    401,
                    "UNAUTHORIZED",
                    "Token faltante, inválido o expirado");

            mapper.writeValue(response.getOutputStream(), error);
            response.flushBuffer();
        }
    }
}
