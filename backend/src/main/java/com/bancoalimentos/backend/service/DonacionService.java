package com.bancoalimentos.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bancoalimentos.backend.dto.DonacionDTO;
import com.bancoalimentos.backend.model.Donacion;
import com.bancoalimentos.backend.exception.ResourceNotFoundException;
import com.bancoalimentos.backend.repository.DonacionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DonacionService {

    private final DonacionRepository donacionRepository;

    public List<DonacionDTO.Response> listarTodas() {
        return donacionRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<DonacionDTO.Response> listarPorEstado(String estado) {
        return donacionRepository.findByEstadoOrderByCreatedAtDesc(estado)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public DonacionDTO.Response buscarPorId(Long id) {
        Donacion donacion = donacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donación no encontrada con id: " + id));
        return toResponse(donacion);
    }

    @Transactional
    public DonacionDTO.Response crear(DonacionDTO.Request request) {
        Donacion donacion = Donacion.builder()
                .donante(request.getDonante())
                .tipoAlimento(request.getTipoAlimento())
                .cantidad(request.getCantidad())
                .unidad(request.getUnidad())
                .fechaDonacion(request.getFechaDonacion() != null ? request.getFechaDonacion() : LocalDate.now())
                .fechaVencimiento(request.getFechaVencimiento())
                .estado("PENDIENTE")
                .observaciones(request.getObservaciones())
                .build();

        return toResponse(donacionRepository.save(donacion));
    }

    @Transactional
    public DonacionDTO.Response actualizar(Long id, DonacionDTO.Request request) {
        Donacion donacion = donacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donación no encontrada con id: " + id));

        donacion.setDonante(request.getDonante());
        donacion.setTipoAlimento(request.getTipoAlimento());
        donacion.setCantidad(request.getCantidad());
        donacion.setUnidad(request.getUnidad());
        donacion.setFechaDonacion(request.getFechaDonacion());
        donacion.setFechaVencimiento(request.getFechaVencimiento());
        donacion.setObservaciones(request.getObservaciones());

        return toResponse(donacionRepository.save(donacion));
    }

    @Transactional
    public DonacionDTO.Response cambiarEstado(Long id, String nuevoEstado) {
        Donacion donacion = donacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donación no encontrada con id: " + id));
        donacion.setEstado(nuevoEstado);
        return toResponse(donacionRepository.save(donacion));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!donacionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Donación no encontrada con id: " + id);
        }
        donacionRepository.deleteById(id);
    }

    // ---------- mapeo entidad → DTO ----------
    private DonacionDTO.Response toResponse(Donacion d) {
        return DonacionDTO.Response.builder()
                .id(d.getId())
                .donante(d.getDonante())
                .tipoAlimento(d.getTipoAlimento())
                .cantidad(d.getCantidad())
                .unidad(d.getUnidad())
                .fechaDonacion(d.getFechaDonacion())
                .fechaVencimiento(d.getFechaVencimiento())
                .estado(d.getEstado())
                .observaciones(d.getObservaciones())
                .createdAt(d.getCreatedAt())
                .build();
    }
}