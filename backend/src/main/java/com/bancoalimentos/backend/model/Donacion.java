package com.bancoalimentos.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "donaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del donante es obligatorio")
    @Column(nullable = false, length = 150)
    private String donante;

    @NotBlank(message = "El tipo de alimento es obligatorio")
    @Column(name = "tipo_alimento", nullable = false, length = 120)
    private String tipoAlimento;

    @NotNull(message = "La cantidad es obligatoria")
    @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor a cero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidad;

    @NotBlank(message = "La unidad es obligatoria")
    @Column(nullable = false, length = 20)
    private String unidad;

    @Column(name = "fecha_donacion", nullable = false)
    private LocalDate fechaDonacion;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(nullable = false, length = 30)
    private String estado;  // PENDIENTE | EN_PROCESO | DISTRIBUIDO | CANCELADO

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.fechaDonacion == null) {
            this.fechaDonacion = LocalDate.now();
        }
        if (this.estado == null) {
            this.estado = "PENDIENTE";
        }
        if (this.unidad == null) {
            this.unidad = "kg";
        }
    }
}