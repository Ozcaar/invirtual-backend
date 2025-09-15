package com.ozcaar.invirtual.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ozcaar.invirtual.auth.service.CustomUserDetailsService;
import com.ozcaar.invirtual.auth.service.JwtFilter;
import com.ozcaar.invirtual.common.security.CustomSecurityHandlers.CustomAccessDeniedHandler;
import com.ozcaar.invirtual.common.security.CustomSecurityHandlers.CustomAuthenticationEntryPoint;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());

        return builder.build();
    }

    private final JwtFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;
    private final Environment environment;

    public SecurityConfig(Environment environment, JwtFilter jwtFilter, CustomUserDetailsService userDetailsService) {
        this.environment = environment;
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    // Configure security filters
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        boolean isInDev = environment.acceptsProfiles(Profiles.of("dev", "test"));

        http.csrf(csrf -> csrf.disable());

        http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.userDetailsService(userDetailsService);

        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/auth/**").permitAll();

            if (isInDev) {
                auth.requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html"
                ).permitAll();
            }

            // auth.requestMatchers("/admin/**").hasRole("ADMIN", "DEV")
            auth.requestMatchers("/admin/**").hasAnyRole("ADMIN", "DEV");
            auth.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN", "DEV");

            auth.anyRequest().authenticated();
        })
        .exceptionHandling(eh -> eh
            .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            .accessDeniedHandler(new CustomAccessDeniedHandler())
        );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
