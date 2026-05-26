package com.bancoalimentos.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad Inventario (sección 2.1.2 del documento).
 * Registra el stock disponible por tipo de alimento en el banco.
 * Se actualiza manualmente o mediante el módulo de distribuciones.
 */
@Entity
@Table(name = "inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El tipo de alimento es obligatorio")
    @Column(name = "tipo_alimento", nullable = false, length = 120)
    private String tipoAlimento;

    @NotNull(message = "El stock disponible es obligatorio")
    @DecimalMin(value = "0.00", message = "El stock no puede ser negativo")
    @Column(name = "stock_disponible", nullable = false, precision = 10, scale = 2)
    private BigDecimal stockDisponible;

    @NotBlank(message = "La unidad es obligatoria")
    @Column(nullable = false, length = 20)
    private String unidad;

    @Column(length = 255)
    private String descripcion;

    @Column(name = "stock_minimo", precision = 10, scale = 2)
    private BigDecimal stockMinimo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt  = LocalDateTime.now();
        this.updatedAt  = LocalDateTime.now();
        if (this.stockDisponible == null) {
            this.stockDisponible = BigDecimal.ZERO;
        }
        if (this.stockMinimo == null) {
            this.stockMinimo = BigDecimal.ZERO;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}