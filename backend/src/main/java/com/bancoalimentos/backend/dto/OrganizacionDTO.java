package com.bancoalimentos.backend.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

// ===============================================================
// DTOs de Organizacion
// ===============================================================
public class OrganizacionDTO {

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Request {
        @NotBlank(message = "El nombre es obligatorio")
        private String nombre;

        private String ruc;

        @NotBlank(message = "El tipo es obligatorio")
        private String tipo;

        private String direccion;
        private String telefono;
        private String responsable;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private Long id;
        private String nombre;
        private String ruc;
        private String tipo;
        private String direccion;
        private String telefono;
        private String responsable;
        private Boolean activo;
        private LocalDateTime createdAt;
    }
}