package com.bancoalimentos.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bancoalimentos.backend.repository.DistribucionRepository;
import com.bancoalimentos.backend.repository.DonacionRepository;
import com.bancoalimentos.backend.repository.OrganizacionRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardController {

    private final DonacionRepository donacionRepository;
    private final OrganizacionRepository organizacionRepository;
    private final DistribucionRepository distribucionRepository;

    @GetMapping("/resumen")
    public ResponseEntity<Map<String, Object>> resumen() {
        Map<String, Object> data = new HashMap<>();

        data.put("totalDonaciones",       donacionRepository.count());
        data.put("donacionesPendientes",  donacionRepository.countByEstado("PENDIENTE"));
        data.put("donacionesEnProceso",   donacionRepository.countByEstado("EN_PROCESO"));
        data.put("donacionesDistribuidas",donacionRepository.countByEstado("DISTRIBUIDO"));

        data.put("totalOrganizaciones",   organizacionRepository.count());
        data.put("organizacionesActivas", organizacionRepository.findByActivoTrueOrderByIdAsc().size());

        data.put("totalDistribuciones",   distribucionRepository.count());
        data.put("distribucionesPendientes", distribucionRepository.countByEstado("PENDIENTE"));
        data.put("distribucionesEntregadas", distribucionRepository.countByEstado("ENTREGADO"));

        return ResponseEntity.ok(data);
    }
}