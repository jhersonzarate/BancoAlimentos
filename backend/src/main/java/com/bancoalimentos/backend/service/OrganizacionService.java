package com.bancoalimentos.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bancoalimentos.backend.dto.OrganizacionDTO;
import com.bancoalimentos.backend.model.Organizacion;
import com.bancoalimentos.backend.exception.ResourceNotFoundException;
import com.bancoalimentos.backend.repository.OrganizacionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrganizacionService {

    private final OrganizacionRepository organizacionRepository;

    public List<OrganizacionDTO.Response> listarActivas() {
        return organizacionRepository.findByActivoTrueOrderByNombreAsc()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<OrganizacionDTO.Response> listarTodas() {
        return organizacionRepository.findAll()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public OrganizacionDTO.Response buscarPorId(Long id) {
        Organizacion org = organizacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organización no encontrada con id: " + id));
        return toResponse(org);
    }

    @Transactional
    public OrganizacionDTO.Response crear(OrganizacionDTO.Request request) {
        Organizacion org = Organizacion.builder()
                .nombre(request.getNombre())
                .ruc(request.getRuc())
                .tipo(request.getTipo())
                .direccion(request.getDireccion())
                .telefono(request.getTelefono())
                .responsable(request.getResponsable())
                .activo(true)
                .build();
        return toResponse(organizacionRepository.save(org));
    }

    @Transactional
    public OrganizacionDTO.Response actualizar(Long id, OrganizacionDTO.Request request) {
        Organizacion org = organizacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organización no encontrada con id: " + id));

        org.setNombre(request.getNombre());
        org.setRuc(request.getRuc());
        org.setTipo(request.getTipo());
        org.setDireccion(request.getDireccion());
        org.setTelefono(request.getTelefono());
        org.setResponsable(request.getResponsable());

        return toResponse(organizacionRepository.save(org));
    }

    @Transactional
    public void desactivar(Long id) {
        Organizacion org = organizacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organización no encontrada con id: " + id));
        org.setActivo(false);
        organizacionRepository.save(org);
    }

    private OrganizacionDTO.Response toResponse(Organizacion o) {
        return OrganizacionDTO.Response.builder()
                .id(o.getId())
                .nombre(o.getNombre())
                .ruc(o.getRuc())
                .tipo(o.getTipo())
                .direccion(o.getDireccion())
                .telefono(o.getTelefono())
                .responsable(o.getResponsable())
                .activo(o.getActivo())
                .createdAt(o.getCreatedAt())
                .build();
    }
}