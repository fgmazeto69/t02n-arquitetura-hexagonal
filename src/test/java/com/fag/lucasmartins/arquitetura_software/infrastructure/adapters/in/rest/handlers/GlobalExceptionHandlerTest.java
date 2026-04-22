package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.handlers;

import com.fag.lucasmartins.arquitetura_software.core.domain.exceptions.DomainException;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.dto.ErrorDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.exceptions.RepositorioException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleDomainException_retorna400() {
        ResponseEntity<ErrorDTO> resp = handler.handleDomainException(new DomainException("erro dom"));
        assertEquals(400, resp.getStatusCodeValue());
        assertTrue(resp.getBody().getMessage().contains("erro dom"));
    }

    @Test
    void handleRepositorioException_retorna400() {
        ResponseEntity<ErrorDTO> resp = handler.handleRepositorioException(new RepositorioException("erro repo"));
        assertEquals(400, resp.getStatusCodeValue());
        assertTrue(resp.getBody().getMessage().contains("erro repo"));
    }

    @Test
    void handleGenericException_retorna500ComMensagemGenerica() {
        ResponseEntity<ErrorDTO> resp = handler.handleGenericException(new RuntimeException("x"));
        assertEquals(500, resp.getStatusCodeValue());
        assertTrue(resp.getBody().getMessage().startsWith("Erro interno no servidor"));
    }
}
