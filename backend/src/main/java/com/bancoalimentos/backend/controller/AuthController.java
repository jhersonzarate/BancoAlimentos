package com.bancoalimentos.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bancoalimentos.backend.config.JwtUtil;
import com.bancoalimentos.backend.dto.AuthDTO;
import com.bancoalimentos.backend.service.AuthService;

import java.util.Map;

/**
 * Controlador de autenticación.
 * Expone los endpoints de registro, login y verificación de token.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {
    "https://banco-alimentos-mcrn.vercel.app",
    "http://localhost:4200"
})
public class AuthController {

    private final AuthService authService;
    private final JwtUtil     jwtUtil;

    // ── Registro ──────────────────────────────────────────────────────────

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@Valid @RequestBody AuthDTO.RegisterRequest request) {
        try {
            AuthDTO.AuthResponse response = authService.registrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        }
    }

    // ── Login ─────────────────────────────────────────────────────────────

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDTO.LoginRequest request) {
        try {
            AuthDTO.AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("mensaje", e.getMessage()));
        }
    }

    // ── Verificación del token ────────────────────────────────────────────

    @GetMapping("/verify")
    public ResponseEntity<?> verify(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("valid", false, "mensaje", "Token no proporcionado"));
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.esValido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("valid", false, "mensaje", "Token inválido o expirado"));
        }

        return ResponseEntity.ok(Map.of(
                "valid", true,
                "email", jwtUtil.extraerEmail(token),
                "rol",   jwtUtil.extraerRol(token)
        ));
    }
}