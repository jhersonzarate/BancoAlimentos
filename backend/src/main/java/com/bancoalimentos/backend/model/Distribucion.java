package com.bancoalimentos.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "distribuciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Distribucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donacion_id", nullable = false)
    private Donacion donacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacion_id", nullable = false)
    private Organizacion organizacion;

    @NotNull(message = "La cantidad entregada es obligatoria")
    @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor a cero")
    @Column(name = "cantidad_entregada", nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidadEntregada;

    @Column(name = "fecha_entrega", nullable = false)
    private LocalDate fechaEntrega;

    @Column(nullable = false, length = 30)
    private String estado;  // PENDIENTE | ENTREGADO | CANCELADO

    @Column(columnDefinition = "TEXT")
    private String notas;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.fechaEntrega == null) {
            this.fechaEntrega = LocalDate.now();
        }
        if (this.estado == null) {
            this.estado = "PENDIENTE";
        }
    }
}