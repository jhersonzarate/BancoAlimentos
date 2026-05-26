package com.bancoalimentos.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bancoalimentos.backend.model.Inventario;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad Inventario.
 */
@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    List<Inventario> findAllByOrderByTipoAlimentoAsc();

    Optional<Inventario> findByTipoAlimentoIgnoreCase(String tipoAlimento);

    boolean existsByTipoAlimentoIgnoreCase(String tipoAlimento);
}