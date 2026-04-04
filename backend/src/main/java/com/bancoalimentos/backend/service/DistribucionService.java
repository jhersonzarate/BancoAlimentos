package com.bancoalimentos.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bancoalimentos.backend.dto.DistribucionDTO;
import com.bancoalimentos.backend.model.Distribucion;
import com.bancoalimentos.backend.model.Donacion;
import com.bancoalimentos.backend.model.Organizacion;
import com.bancoalimentos.backend.exception.ResourceNotFoundException;
import com.bancoalimentos.backend.repository.DistribucionRepository;
import com.bancoalimentos.backend.repository.DonacionRepository;
import com.bancoalimentos.backend.repository.OrganizacionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DistribucionService {

    private final DistribucionRepository distribucionRepository;
    private final DonacionRepository donacionRepository;
    private final OrganizacionRepository organizacionRepository;

    public List<DistribucionDTO.Response> listarTodas() {
        return distribucionRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public DistribucionDTO.Response buscarPorId(Long id) {
        Distribucion dist = distribucionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Distribución no encontrada con id: " + id));
        return toResponse(dist);
    }

    @Transactional
    public DistribucionDTO.Response crear(DistribucionDTO.Request request) {
        Donacion donacion = donacionRepository.findById(request.getDonacionId())
                .orElseThrow(() -> new ResourceNotFoundException("Donación no encontrada con id: " + request.getDonacionId()));

        Organizacion organizacion = organizacionRepository.findById(request.getOrganizacionId())
                .orElseThrow(() -> new ResourceNotFoundException("Organización no encontrada con id: " + request.getOrganizacionId()));

        Distribucion distribucion = Distribucion.builder()
                .donacion(donacion)
                .organizacion(organizacion)
                .cantidadEntregada(request.getCantidadEntregada())
                .fechaEntrega(request.getFechaEntrega() != null ? request.getFechaEntrega() : LocalDate.now())
                .estado("PENDIENTE")
                .notas(request.getNotas())
                .build();

        // Actualizar estado de la donación
        donacion.setEstado("EN_PROCESO");
        donacionRepository.save(donacion);

        return toResponse(distribucionRepository.save(distribucion));
    }

    @Transactional
    public DistribucionDTO.Response marcarEntregado(Long id) {
        Distribucion dist = distribucionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Distribución no encontrada con id: " + id));
        dist.setEstado("ENTREGADO");
        return toResponse(distribucionRepository.save(dist));
    }

    @Transactional
    public DistribucionDTO.Response cancelar(Long id) {
        Distribucion dist = distribucionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Distribución no encontrada con id: " + id));
        dist.setEstado("CANCELADO");
        return toResponse(distribucionRepository.save(dist));
    }

    private DistribucionDTO.Response toResponse(Distribucion d) {
        return DistribucionDTO.Response.builder()
                .id(d.getId())
                .donacionId(d.getDonacion().getId())
                .donanteDonacion(d.getDonacion().getDonante())
                .tipoAlimento(d.getDonacion().getTipoAlimento())
                .organizacionId(d.getOrganizacion().getId())
                .nombreOrganizacion(d.getOrganizacion().getNombre())
                .cantidadEntregada(d.getCantidadEntregada())
                .unidad(d.getDonacion().getUnidad())
                .fechaEntrega(d.getFechaEntrega())
                .estado(d.getEstado())
                .notas(d.getNotas())
                .createdAt(d.getCreatedAt())
                .build();
    }
}