package com.bancoalimentos.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad:
 * - Sesiones sin estado (JWT, no cookies).
 * - Rutas públicas: registro y login.
 * - Rutas de solo ADMIN: gestión de usuarios.
 * - Todo lo demás requiere estar autenticado.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilitar CSRF (no se usan cookies de sesión)
            .csrf(AbstractHttpConfigurer::disable)

            // Configurar CORS desde WebConfig.java
            .cors(cors -> cors.configure(http))

            // Sin estado: cada request trae su propio token JWT
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Reglas de autorización por ruta
            .authorizeHttpRequests(auth -> auth

                // Rutas públicas (registro y login)
                .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()

                // Solo ADMIN puede gestionar usuarios
                .requestMatchers("/api/usuarios/**").hasRole("ADMIN")

                // Todo lo demás requiere autenticación válida
                .anyRequest().authenticated()
            )

            // Agregar el filtro JWT antes del filtro estándar de Spring Security
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}