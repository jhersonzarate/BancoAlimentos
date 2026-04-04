package com.bancoalimentos.backend.exception;

// ---------------------------------------------------------------
// Excepción personalizada para recursos no encontrados
// ---------------------------------------------------------------
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}