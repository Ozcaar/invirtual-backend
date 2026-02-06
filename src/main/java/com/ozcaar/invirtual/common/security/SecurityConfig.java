package com.ozcaar.invirtual.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozcaar.invirtual.common.exception.global.JwtAuthEntryPoint;
import com.ozcaar.invirtual.common.security.CustomSecurityHandlers.CustomAccessDeniedHandler;
import com.ozcaar.invirtual.common.security.CustomSecurityHandlers.CustomAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

@EnableMethodSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final Environment environment;

    // Configure security filters
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
            SupabaseJwtAuthenticationConverter converter, ObjectMapper mapper) throws Exception {

        boolean isInDev = environment.acceptsProfiles(Profiles.of("dev", "test"));

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/auth/**", "/api/public/**").permitAll();

                    if (isInDev) {
                        auth.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll();
                    }

                    auth.requestMatchers("/admin/**").hasAnyRole("ADMIN", "DEV");
                    auth.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN", "DEV");
                    auth.anyRequest().authenticated();
                })
                .exceptionHandling(eh -> eh
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint(mapper))
                        .accessDeniedHandler(new CustomAccessDeniedHandler(mapper)))
                .oauth2ResourceServer(oauth -> oauth
                        .authenticationEntryPoint(new JwtAuthEntryPoint())
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder())
                                .jwtAuthenticationConverter(converter)));

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        JwtDecoder decoder = JwtDecoders.fromIssuerLocation(
                "https://jooaptsldvtinckgvwhx.supabase.co/auth/v1");

        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators
                .createDefaultWithIssuer("https://jooaptsldvtinckgvwhx.supabase.co/auth/v1");

        OAuth2TokenValidator<Jwt> audienceValidator = jwt -> {
            var aud = jwt.getAudience();
            return (aud != null && aud.contains("authenticated"))
                    ? OAuth2TokenValidatorResult.success()
                    : OAuth2TokenValidatorResult.failure(
                            new OAuth2Error("invalid_token", "Invalid audience", null));
        };

        NimbusJwtDecoder nimbus = (NimbusJwtDecoder) decoder;
        nimbus.setJwtValidator(new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator));
        return nimbus;
    }
}
