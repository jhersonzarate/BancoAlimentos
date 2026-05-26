package com.bancoalimentos.backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Filtro JWT que se ejecuta una vez por request.
 * Extrae el token del header Authorization, lo valida con JwtUtil
 * y carga la autenticación en el SecurityContext de Spring.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Si no hay header válido, continuar sin autenticar (Spring Security rechazará rutas protegidas)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); // Quitar el prefijo "Bearer "

        if (!jwtUtil.esValido(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer datos del token y construir la autenticación
        String email = jwtUtil.extraerEmail(token);
        String rol   = jwtUtil.extraerRol(token);

        // ROLE_ es el prefijo que espera Spring Security para sus authorities
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + rol);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(email, null, List.of(authority));

        // Registrar la autenticación en el contexto de seguridad de Spring
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}