package com.bancoalimentos.backend.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

// ---------------------------------------------------------------
// DTO para crear / actualizar donaciones (Request)
// ---------------------------------------------------------------
public class DonacionDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @NotBlank(message = "El donante es obligatorio")
        private String donante;

        @NotBlank(message = "El tipo de alimento es obligatorio")
        private String tipoAlimento;

        @NotNull(message = "La cantidad es obligatoria")
        @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor a cero")
        private BigDecimal cantidad;

        @NotBlank(message = "La unidad es obligatoria")
        private String unidad;

        private LocalDate fechaDonacion;
        private LocalDate fechaVencimiento;
        private String observaciones;
    }

    // ---------------------------------------------------------------
    // DTO para respuesta al frontend (Response)
    // ---------------------------------------------------------------
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String donante;
        private String tipoAlimento;
        private BigDecimal cantidad;
        private String unidad;
        private LocalDate fechaDonacion;
        private LocalDate fechaVencimiento;
        private String estado;
        private String observaciones;
        private LocalDateTime createdAt;
    }
}