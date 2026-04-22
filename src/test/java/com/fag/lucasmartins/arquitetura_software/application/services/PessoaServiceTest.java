package com.fag.lucasmartins.arquitetura_software.application.services;

import com.fag.lucasmartins.arquitetura_software.application.ports.out.persistence.PessoaRepositoryPort;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PessoaBO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PessoaServiceTest {

    private PessoaRepositoryPort pessoaRepositoryPort;
    private PessoaService service;

    @BeforeEach
    void setup() {
        pessoaRepositoryPort = mock(PessoaRepositoryPort.class);
        service = new PessoaService(pessoaRepositoryPort);
    }

    @Test
    void salvar_deveValidarEAoFinalPersistir() {
        PessoaBO entrada = new PessoaBO();
        entrada.setNomeCompleto("Fulano");
        entrada.setCpf("12345678910");
        entrada.setDataNascimento(LocalDate.now().minusYears(25));
        entrada.setEmail("a@b.com");
        entrada.setTelefone("41999999999");

        when(pessoaRepositoryPort.salvar(any())).thenAnswer(inv -> inv.getArgument(0));

        PessoaBO saida = service.salvar(entrada);

        verify(pessoaRepositoryPort).salvar(any());
        assertNotNull(saida);
    }
}
