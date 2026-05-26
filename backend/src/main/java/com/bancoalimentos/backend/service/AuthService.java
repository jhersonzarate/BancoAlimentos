package com.bancoalimentos.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bancoalimentos.backend.config.JwtUtil;
import com.bancoalimentos.backend.dto.AuthDTO;
import com.bancoalimentos.backend.model.Usuario;
import com.bancoalimentos.backend.repository.UsuarioRepository;

/**
 * Servicio de autenticación.
 * Registra nuevos usuarios y valida credenciales en el login.
 * Genera JWT real con HMAC-SHA256 a través de JwtUtil.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil           jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ── Registro ──────────────────────────────────────────────────────────

    @Transactional
    public AuthDTO.AuthResponse registrar(AuthDTO.RegisterRequest request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Ya existe una cuenta con ese email");
        }

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail().toLowerCase().trim())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .rol("USUARIO")
                .activo(true)
                .build();

        usuario = usuarioRepository.save(usuario);

        String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getRol());

        return buildResponse(usuario, token);
    }

    // ── Login ─────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public AuthDTO.AuthResponse login(AuthDTO.LoginRequest request) {

        Usuario usuario = usuarioRepository
                .findByEmail(request.getEmail().toLowerCase().trim())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales incorrectas"));

        if (!usuario.getActivo()) {
            throw new IllegalArgumentException("Cuenta desactivada. Contacte al administrador.");
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            throw new IllegalArgumentException("Credenciales incorrectas");
        }

        String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getRol());

        return buildResponse(usuario, token);
    }

    // ── Mapeo interno ─────────────────────────────────────────────────────

    private AuthDTO.AuthResponse buildResponse(Usuario usuario, String token) {
        return AuthDTO.AuthResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .token(token)
                .createdAt(usuario.getCreatedAt())
                .build();
    }
}