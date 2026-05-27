error id: file:///C:/Users/Jherson%20Silva/BancoAlimentos/backend/src/main/java/com/bancoalimentos/backend/service/InventarioService.java:com/bancoalimentos/backend/repository/InventarioRepository#findAllByOrderByTipoAlimentoAsc#stream#
file:///C:/Users/Jherson%20Silva/BancoAlimentos/backend/src/main/java/com/bancoalimentos/backend/service/InventarioService.java
empty definition using pc, found symbol in pc: com/bancoalimentos/backend/repository/InventarioRepository#findAllByOrderByTipoAlimentoAsc#stream#
semanticdb not found
empty definition using fallback
non-local guesses:

offset: 1041
uri: file:///C:/Users/Jherson%20Silva/BancoAlimentos/backend/src/main/java/com/bancoalimentos/backend/service/InventarioService.java
text:
```scala
package com.bancoalimentos.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bancoalimentos.backend.dto.InventarioDTO;
import com.bancoalimentos.backend.exception.ResourceNotFoundException;
import com.bancoalimentos.backend.model.Inventario;
import com.bancoalimentos.backend.repository.InventarioRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para el módulo de Inventario.
 * Gestiona el stock disponible de cada tipo de alimento.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    // ── Listar todo el inventario ─────────────────────────────────────────

    public List<InventarioDTO.Response> listarTodo() {
        return inventarioRepository.findAllByOrderByIdAsc()
                .st@@ream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ── Buscar por ID ─────────────────────────────────────────────────────

    public InventarioDTO.Response buscarPorId(Long id) {
        return toResponse(obtenerOLanzar(id));
    }

    // ── Crear nuevo ítem ──────────────────────────────────────────────────

    @Transactional
    public InventarioDTO.Response crear(InventarioDTO.Request request) {
        if (inventarioRepository.existsByTipoAlimentoIgnoreCase(request.getTipoAlimento())) {
            throw new IllegalArgumentException(
                    "Ya existe un registro de inventario para: " + request.getTipoAlimento());
        }

        Inventario inventario = Inventario.builder()
                .tipoAlimento(request.getTipoAlimento())
                .stockDisponible(request.getStockDisponible())
                .unidad(request.getUnidad())
                .descripcion(request.getDescripcion())
                .stockMinimo(request.getStockMinimo() != null
                        ? request.getStockMinimo()
                        : BigDecimal.ZERO)
                .build();

        return toResponse(inventarioRepository.save(inventario));
    }

    // ── Actualizar ítem ───────────────────────────────────────────────────

    @Transactional
    public InventarioDTO.Response actualizar(Long id, InventarioDTO.Request request) {
        Inventario inventario = obtenerOLanzar(id);

        // Si cambia el tipo de alimento, verificar que no exista ya ese nombre
        if (!inventario.getTipoAlimento().equalsIgnoreCase(request.getTipoAlimento())
                && inventarioRepository.existsByTipoAlimentoIgnoreCase(request.getTipoAlimento())) {
            throw new IllegalArgumentException(
                    "Ya existe un registro de inventario para: " + request.getTipoAlimento());
        }

        inventario.setTipoAlimento(request.getTipoAlimento());
        inventario.setStockDisponible(request.getStockDisponible());
        inventario.setUnidad(request.getUnidad());
        inventario.setDescripcion(request.getDescripcion());

        if (request.getStockMinimo() != null) {
            inventario.setStockMinimo(request.getStockMinimo());
        }

        return toResponse(inventarioRepository.save(inventario));
    }

    // ── Eliminar ítem ─────────────────────────────────────────────────────

    @Transactional
    public void eliminar(Long id) {
        if (!inventarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ítem de inventario no encontrado con id: " + id);
        }
        inventarioRepository.deleteById(id);
    }

    // ── Métodos privados ──────────────────────────────────────────────────

    private Inventario obtenerOLanzar(Long id) {
        return inventarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Ítem de inventario no encontrado con id: " + id));
    }

    private InventarioDTO.Response toResponse(Inventario i) {
        boolean bajoStock = i.getStockMinimo() != null
                && i.getStockDisponible().compareTo(i.getStockMinimo()) < 0;

        return InventarioDTO.Response.builder()
                .id(i.getId())
                .tipoAlimento(i.getTipoAlimento())
                .stockDisponible(i.getStockDisponible())
                .unidad(i.getUnidad())
                .descripcion(i.getDescripcion())
                .stockMinimo(i.getStockMinimo())
                .bajoStock(bajoStock)
                .createdAt(i.getCreatedAt())
                .updatedAt(i.getUpdatedAt())
                .build();
    }
}
```


#### Short summary: 

empty definition using pc, found symbol in pc: com/bancoalimentos/backend/repository/InventarioRepository#findAllByOrderByTipoAlimentoAsc#stream#