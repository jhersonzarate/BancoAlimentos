package com.bancoalimentos.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bancoalimentos.backend.dto.UsuarioDTO;
import com.bancoalimentos.backend.service.UsuarioService;

import java.util.List;

/**
 * Controlador para la gestión de usuarios.
 * Todos los endpoints requieren rol ADMIN (configurado en SecurityConfig).
 *
 * AN4 (Admin) puede:
 *   - Ver todos los usuarios
 *   - Editar rol y datos de un usuario
 *   - Activar o desactivar una cuenta
 */
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = {
    "https://banco-alimentos-mcrn.vercel.app",
    "http://localhost:4200"
})
public class UsuarioController {

    private final UsuarioService usuarioService;

    // GET /api/usuarios — lista todos los usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDTO.Response>> listar() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    // GET /api/usuarios/{id} — detalle de un usuario
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO.Response> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    // PUT /api/usuarios/{id} — actualizar nombre, email, rol y estado
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO.Response> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO.ActualizarRequest request) {
        return ResponseEntity.ok(usuarioService.actualizar(id, request));
    }

    // PATCH /api/usuarios/{id}/activar — activar una cuenta desactivada
    @PatchMapping("/{id}/activar")
    public ResponseEntity<UsuarioDTO.Response> activar(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.activar(id));
    }

    // DELETE /api/usuarios/{id} — desactivar una cuenta (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        usuarioService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}