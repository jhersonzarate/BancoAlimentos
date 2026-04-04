package com.bancoalimentos.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bancoalimentos.backend.model.Organizacion;

import java.util.List;

@Repository
public interface OrganizacionRepository extends JpaRepository<Organizacion, Long> {

    List<Organizacion> findByActivoTrueOrderByNombreAsc();

    boolean existsByRuc(String ruc);
}