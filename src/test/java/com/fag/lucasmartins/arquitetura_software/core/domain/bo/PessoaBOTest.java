package com.fag.lucasmartins.arquitetura_software.core.domain.bo;

import com.fag.lucasmartins.arquitetura_software.core.domain.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PessoaBOTest {

    @Test
    void validarCamposObrigatorios_deveLancarQuandoFaltaNome() {
        PessoaBO bo = new PessoaBO();
        bo.setCpf("123");
        bo.setDataNascimento(LocalDate.now().minusYears(20));
        bo.setEmail("a@b.com");
        bo.setTelefone("41999999999");

        DomainException ex = assertThrows(DomainException.class, bo::validarCamposObrigatorios);
        assertTrue(ex.getMessage().contains("Nome completo"));
    }

    @Test
    void validarCamposObrigatorios_normalizaCpfETelefone() {
        PessoaBO bo = new PessoaBO();
        bo.setNomeCompleto("Teste");
        bo.setCpf("123.456.789-10");
        bo.setDataNascimento(LocalDate.now().minusYears(20));
        bo.setEmail("a@b.com");
        bo.setTelefone("(41) 99999-8888");

        bo.validarCamposObrigatorios();

        assertEquals("12345678910", bo.getCpf());
        assertEquals("41999998888", bo.getTelefone());
    }

    @Test
    void validarEmail_deveLancarQuandoInvalido() {
        PessoaBO bo = new PessoaBO();
        bo.setEmail("email-invalido");
        DomainException ex = assertThrows(DomainException.class, bo::validarEmail);
        assertEquals("E-mail inválido", ex.getMessage());
    }

    @Test
    void validarTelefone_deveLancarQuandoTamanhoDiferenteDe11() {
        PessoaBO bo = new PessoaBO();
        bo.setTelefone("123");
        DomainException ex = assertThrows(DomainException.class, bo::validarTelefone);
        assertTrue(ex.getMessage().contains("11 dígitos"));
    }

    @Test
    void validarCpf_deveLancarQuandoTamanhoDiferenteDe11() {
        PessoaBO bo = new PessoaBO();
        bo.setCpf("123");
        DomainException ex = assertThrows(DomainException.class, bo::validarCpf);
        assertTrue(ex.getMessage().contains("11 dígitos"));
    }

    @Test
    void validarMaioridade_deveLancarQuandoMenorDe18() {
        PessoaBO bo = new PessoaBO();
        bo.setDataNascimento(LocalDate.now().minusYears(17));
        DomainException ex = assertThrows(DomainException.class, bo::validarMaioridade);
        assertTrue(ex.getMessage().contains("18 anos"));
    }
}
