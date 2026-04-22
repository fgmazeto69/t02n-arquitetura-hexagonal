package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.repository;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.entity.ProdutoEntity;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.exceptions.RepositorioException;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.jpa.ProdutoJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoRepositoryAdapterTest {

    private ProdutoJpaRepository jpa;
    private ProdutoRepositoryAdapter adapter;

    @BeforeEach
    void setup() {
        jpa = mock(ProdutoJpaRepository.class);
        adapter = new ProdutoRepositoryAdapter(jpa);
    }

    @Test
    void salvar_devePersistirEMapear() {
        ProdutoBO bo = new ProdutoBO();
        bo.setId(1);
        bo.setNome("X");
        bo.setPreco(2.0);
        bo.setEstoque(3);

        when(jpa.save(any())).thenAnswer(inv -> inv.getArgument(0));

        ProdutoBO salvo = adapter.salvar(bo);
        assertEquals("X", salvo.getNome());
    }

    @Test
    void salvarTodos_devePersistirLista() {
        ProdutoBO b1 = new ProdutoBO(); b1.setId(1); b1.setNome("A");
        ProdutoBO b2 = new ProdutoBO(); b2.setId(2); b2.setNome("B");

        when(jpa.saveAll(any())).thenAnswer(inv -> inv.getArgument(0));

        var out = adapter.salvarTodos(Arrays.asList(b1, b2));
        assertEquals(2, out.size());
        assertEquals("A", out.get(0).getNome());
    }

    @Test
    void encontrarPorId_deveLancarQuandoVazio() {
        when(jpa.findById(9)).thenReturn(Optional.empty());
        RepositorioException ex = assertThrows(RepositorioException.class, () -> adapter.encontrarPorId(9));
        assertTrue(ex.getMessage().contains("9"));
    }

    @Test
    void encontrarPorIds_deveConverterLista() {
        ProdutoEntity e = new ProdutoEntity();
        e.setId(5); e.setNome("N"); e.setPreco(1.0); e.setEstoque(2);
        when(jpa.findByIdIn(any())).thenReturn(Collections.singletonList(e));

        var lista = adapter.encontrarPorIds(Collections.singletonList(5));
        assertEquals(1, lista.size());
        assertEquals(5, lista.get(0).getId());
        assertEquals("N", lista.get(0).getNome());
    }
}
