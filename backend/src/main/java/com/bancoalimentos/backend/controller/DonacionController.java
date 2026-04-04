package com.bancoalimentos.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bancoalimentos.backend.dto.DonacionDTO;
import com.bancoalimentos.backend.service.DonacionService;

import java.util.List;

@RestController
@RequestMapping("/api/donaciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DonacionController {

    private final DonacionService donacionService;

    @GetMapping
    public ResponseEntity<List<DonacionDTO.Response>> listar(
            @RequestParam(required = false) String estado) {
        if (estado != null && !estado.isBlank()) {
            return ResponseEntity.ok(donacionService.listarPorEstado(estado.toUpperCase()));
        }
        return ResponseEntity.ok(donacionService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonacionDTO.Response> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(donacionService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<DonacionDTO.Response> crear(@Valid @RequestBody DonacionDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(donacionService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonacionDTO.Response> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody DonacionDTO.Request request) {
        return ResponseEntity.ok(donacionService.actualizar(id, request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<DonacionDTO.Response> cambiarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        return ResponseEntity.ok(donacionService.cambiarEstado(id, estado.toUpperCase()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        donacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}