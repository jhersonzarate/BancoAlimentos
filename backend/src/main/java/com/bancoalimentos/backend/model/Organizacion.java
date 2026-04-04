package com.bancoalimentos.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "organizaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(unique = true, length = 11)
    private String ruc;

    @NotBlank(message = "El tipo de organización es obligatorio")
    @Column(nullable = false, length = 80)
    private String tipo;

    @Column(length = 255)
    private String direccion;

    @Column(length = 20)
    private String telefono;

    @Column(length = 120)
    private String responsable;

    @Column(nullable = false)
    private Boolean activo;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.activo == null) {
            this.activo = true;
        }
    }
}