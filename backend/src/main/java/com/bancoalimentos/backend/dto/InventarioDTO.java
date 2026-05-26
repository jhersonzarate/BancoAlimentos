package com.bancoalimentos.backend.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTOs para el módulo de Inventario.
 */
public class InventarioDTO {

    // ── Request: crear o actualizar un ítem de inventario ─────────────────

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @NotBlank(message = "El tipo de alimento es obligatorio")
        private String tipoAlimento;

        @NotNull(message = "El stock disponible es obligatorio")
        @DecimalMin(value = "0.00", message = "El stock no puede ser negativo")
        private BigDecimal stockDisponible;

        @NotBlank(message = "La unidad es obligatoria")
        private String unidad;

        private String     descripcion;
        private BigDecimal stockMinimo;
    }

    // ── Response: datos que se devuelven al frontend ───────────────────────

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long          id;
        private String        tipoAlimento;
        private BigDecimal    stockDisponible;
        private String        unidad;
        private String        descripcion;
        private BigDecimal    stockMinimo;
        private boolean       bajoStock;       // true si stock < stockMinimo
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}