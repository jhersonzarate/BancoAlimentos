package com.bancoalimentos.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bancoalimentos.backend.dto.DistribucionDTO;
import com.bancoalimentos.backend.service.DistribucionService;

import java.util.List;

@RestController
@RequestMapping("/api/distribuciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DistribucionController {

    private final DistribucionService distribucionService;

    @GetMapping
    public ResponseEntity<List<DistribucionDTO.Response>> listar() {
        return ResponseEntity.ok(distribucionService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DistribucionDTO.Response> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(distribucionService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<DistribucionDTO.Response> crear(
            @Valid @RequestBody DistribucionDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(distribucionService.crear(request));
    }

    @PatchMapping("/{id}/entregar")
    public ResponseEntity<DistribucionDTO.Response> marcarEntregado(@PathVariable Long id) {
        return ResponseEntity.ok(distribucionService.marcarEntregado(id));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<DistribucionDTO.Response> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(distribucionService.cancelar(id));
    }
}