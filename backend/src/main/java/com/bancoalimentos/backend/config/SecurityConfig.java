package com.bancoalimentos.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Deshabilita la autenticación automática de Spring Security.
 * La autenticación se maneja manualmente en AuthController/AuthService.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configure(http)) // usa la config de WebConfig.java
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}