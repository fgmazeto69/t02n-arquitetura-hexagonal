package com.fag.lucasmartins.arquitetura_software.application.services;

import com.fag.lucasmartins.arquitetura_software.application.ports.out.persistence.ProdutoRepositoryPort;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoServiceTest {

    private ProdutoRepositoryPort produtoRepositoryPort;
    private ProdutoService service;

    @BeforeEach
    void setup() {
        produtoRepositoryPort = mock(ProdutoRepositoryPort.class);
        service = new ProdutoService(produtoRepositoryPort);
    }

    @Test
    void salvar_deveValidarCalcularEChamarRepositorio() {
        ProdutoBO entrada = new ProdutoBO();
        entrada.setNome("Basico");
        entrada.setPreco(100.0);
        entrada.setEstoque(50);

        when(produtoRepositoryPort.salvar(any())).thenAnswer(inv -> inv.getArgument(0));

        ProdutoBO saida = service.salvar(entrada);

        ArgumentCaptor<ProdutoBO> captor = ArgumentCaptor.forClass(ProdutoBO.class);
        verify(produtoRepositoryPort).salvar(captor.capture());
        ProdutoBO enviado = captor.getValue();

        assertEquals(90.0, enviado.getPrecoFinal(), 0.0001);
        assertSame(enviado, saida);
    }
}
