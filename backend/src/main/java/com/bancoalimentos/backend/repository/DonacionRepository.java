package com.bancoalimentos.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bancoalimentos.backend.model.Donacion;

import java.util.List;

@Repository
public interface DonacionRepository extends JpaRepository<Donacion, Long> {

    List<Donacion> findByEstadoOrderByIdAsc(String estado);
    
    List<Donacion> findAllByOrderByIdAsc();

    long countByEstado(String estado);
}