package com.bancoalimentos.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bancoalimentos.backend.model.Distribucion;

import java.util.List;

@Repository
public interface DistribucionRepository extends JpaRepository<Distribucion, Long> {

    List<Distribucion> findByDonacionIdOrderByIdAsc(Long donacionId);
    
    List<Distribucion> findByOrganizacionIdOrderByIdAsc(Long organizacionId);
    
    List<Distribucion> findAllByOrderByIdAsc();

    long countByEstado(String estado);
}