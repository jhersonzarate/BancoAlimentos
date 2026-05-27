package com.bancoalimentos.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bancoalimentos.backend.dto.UsuarioDTO;
import com.bancoalimentos.backend.exception.ResourceNotFoundException;
import com.bancoalimentos.backend.model.Usuario;
import com.bancoalimentos.backend.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de usuarios.
 * Permite al ADMIN listar, actualizar y desactivar cuentas.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // ── Listar todos los usuarios ─────────────────────────────────────────

    public List<UsuarioDTO.Response> listarTodos() {
        return usuarioRepository.findAllByOrderByIdAsc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ── Buscar usuario por ID ─────────────────────────────────────────────

    public UsuarioDTO.Response buscarPorId(Long id) {
        Usuario usuario = obtenerOLanzar(id);
        return toResponse(usuario);
    }

    // ── Actualizar nombre, email y rol ────────────────────────────────────

    @Transactional
    public UsuarioDTO.Response actualizar(Long id, UsuarioDTO.ActualizarRequest request) {
        Usuario usuario = obtenerOLanzar(id);

        // Verificar que el nuevo email no esté en uso por otro usuario
        if (!usuario.getEmail().equals(request.getEmail())
                && usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El email ya está en uso por otro usuario");
        }

        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail().toLowerCase().trim());
        usuario.setRol(request.getRol());

        if (request.getActivo() != null) {
            usuario.setActivo(request.getActivo());
        }

        return toResponse(usuarioRepository.save(usuario));
    }

    // ── Desactivar cuenta (no se elimina, se desactiva) ───────────────────

    @Transactional
    public void desactivar(Long id) {
        Usuario usuario = obtenerOLanzar(id);
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    // ── Activar cuenta ────────────────────────────────────────────────────

    @Transactional
    public UsuarioDTO.Response activar(Long id) {
        Usuario usuario = obtenerOLanzar(id);
        usuario.setActivo(true);
        return toResponse(usuarioRepository.save(usuario));
    }

    // ── Métodos privados ──────────────────────────────────────────────────

    private Usuario obtenerOLanzar(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado con id: " + id));
    }

    private UsuarioDTO.Response toResponse(Usuario u) {
        return UsuarioDTO.Response.builder()
                .id(u.getId())
                .nombre(u.getNombre())
                .email(u.getEmail())
                .rol(u.getRol())
                .activo(u.getActivo())
                .createdAt(u.getCreatedAt())
                .build();
    }
}