package com.bancoalimentos.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bancoalimentos.backend.dto.OrganizacionDTO;
import com.bancoalimentos.backend.service.OrganizacionService;

import java.util.List;

@RestController
@RequestMapping("/api/organizaciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class OrganizacionController {

    private final OrganizacionService organizacionService;

    @GetMapping
    public ResponseEntity<List<OrganizacionDTO.Response>> listar(
            @RequestParam(defaultValue = "false") boolean todas) {
        if (todas) {
            return ResponseEntity.ok(organizacionService.listarTodas());
        }
        return ResponseEntity.ok(organizacionService.listarActivas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizacionDTO.Response> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(organizacionService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<OrganizacionDTO.Response> crear(@Valid @RequestBody OrganizacionDTO.Request request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizacionService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganizacionDTO.Response> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody OrganizacionDTO.Request request) {
        return ResponseEntity.ok(organizacionService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        organizacionService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}