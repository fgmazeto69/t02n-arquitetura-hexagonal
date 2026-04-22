package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.repository;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PessoaBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.entity.PessoaEntity;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.exceptions.RepositorioException;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.jpa.PessoaJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PessoaRepositoryAdapterTest {

    private PessoaJpaRepository jpa;
    private PessoaRepositoryAdapter adapter;

    @BeforeEach
    void setup() {
        jpa = mock(PessoaJpaRepository.class);
        adapter = new PessoaRepositoryAdapter(jpa);
    }

    @Test
    void salvar_devePersistirEMapear() {
        PessoaBO bo = new PessoaBO();
        bo.setId(1);
        bo.setNomeCompleto("Fulano");
        when(jpa.save(any())).thenAnswer(inv -> inv.getArgument(0));

        PessoaBO salvo = adapter.salvar(bo);
        assertEquals("Fulano", salvo.getNomeCompleto());
    }

    @Test
    void encontrarPorId_quandoPresente_deveRetornarBO() {
        PessoaEntity e = new PessoaEntity();
        e.setId(2); e.setNomeCompleto("Beltrano");
        when(jpa.findById(2)).thenReturn(Optional.of(e));

        PessoaBO bo = adapter.encontrarPorId(2);
        assertEquals(2, bo.getId());
        assertEquals("Beltrano", bo.getNomeCompleto());
    }

    @Test
    void encontrarPorId_quandoVazio_deveLancar() {
        when(jpa.findById(3)).thenReturn(Optional.empty());
        assertThrows(RepositorioException.class, () -> adapter.encontrarPorId(3));
    }
}
