package com.bancoalimentos.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bancoalimentos.backend.dto.InventarioDTO;
import com.bancoalimentos.backend.service.InventarioService;

import java.util.List;

/**
 * Controlador para el módulo de Inventario.
 * Entidad del negocio documentada en la sección 2.1.2 del informe.
 *
 * Registra el stock disponible por tipo de alimento en el banco.
 */
@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
@CrossOrigin(origins = {
    "https://banco-alimentos-mcrn.vercel.app",
    "http://localhost:4200"
})
public class InventarioController {

    private final InventarioService inventarioService;

    // GET /api/inventario — lista todo el inventario
    @GetMapping
    public ResponseEntity<List<InventarioDTO.Response>> listar() {
        return ResponseEntity.ok(inventarioService.listarTodo());
    }

    // GET /api/inventario/{id} — detalle de un ítem
    @GetMapping("/{id}")
    public ResponseEntity<InventarioDTO.Response> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.buscarPorId(id));
    }

    // POST /api/inventario — crear nuevo ítem
    @PostMapping
    public ResponseEntity<InventarioDTO.Response> crear(
            @Valid @RequestBody InventarioDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventarioService.crear(request));
    }

    // PUT /api/inventario/{id} — actualizar ítem
    @PutMapping("/{id}")
    public ResponseEntity<InventarioDTO.Response> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody InventarioDTO.Request request) {
        return ResponseEntity.ok(inventarioService.actualizar(id, request));
    }

    // DELETE /api/inventario/{id} — eliminar ítem
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inventarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}