package com.bancoalimentos.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.time.LocalDateTime;

/**
 * DTOs para el módulo de Gestión de Usuarios.
 * Solo el ADMIN puede usar estos endpoints.
 */
public class UsuarioDTO {

    // ── Request: actualizar rol o estado de un usuario ────────────────────

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ActualizarRequest {

        @NotBlank(message = "El nombre es obligatorio")
        private String nombre;

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Email inválido")
        private String email;

        @NotBlank(message = "El rol es obligatorio")
        @Pattern(
            regexp = "^(USUARIO|ADMIN)$",
            message = "El rol debe ser USUARIO o ADMIN"
        )
        private String rol;

        private Boolean activo;
    }

    // ── Response: datos del usuario que se devuelven al frontend ──────────

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long          id;
        private String        nombre;
        private String        email;
        private String        rol;
        private Boolean       activo;
        private LocalDateTime createdAt;
    }
}