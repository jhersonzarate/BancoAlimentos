package com.bancoalimentos.backend.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DistribucionDTO {

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Request {

        @NotNull(message = "La donación es obligatoria")
        private Long donacionId;

        @NotNull(message = "La organización es obligatoria")
        private Long organizacionId;

        @NotNull(message = "La cantidad entregada es obligatoria")
        @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor a cero")
        private BigDecimal cantidadEntregada;

        private LocalDate fechaEntrega;
        private String notas;
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private Long id;
        private Long donacionId;
        private String donanteDonacion;
        private String tipoAlimento;
        private Long organizacionId;
        private String nombreOrganizacion;
        private BigDecimal cantidadEntregada;
        private String unidad;
        private LocalDate fechaEntrega;
        private String estado;
        private String notas;
        private LocalDateTime createdAt;
    }
}