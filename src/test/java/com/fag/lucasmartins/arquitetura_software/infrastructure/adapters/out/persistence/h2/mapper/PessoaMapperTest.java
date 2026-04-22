package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.mapper;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PessoaBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.entity.PessoaEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PessoaMapperTest {

    @Test
    void toEntity_e_toBO_devemMapearTodosOsCampos() {
        PessoaBO bo = new PessoaBO();
        bo.setId(1);
        bo.setNomeCompleto("Fulano");
        bo.setCpf("12345678910");
        bo.setDataNascimento(LocalDate.of(2000, 1, 1));
        bo.setEmail("a@b.com");
        bo.setTelefone("41999999999");

        PessoaEntity entity = PessoaMapper.toEntity(bo);
        assertNotNull(entity);
        assertEquals(1, entity.getId());
        assertEquals("Fulano", entity.getNomeCompleto());
        assertEquals("12345678910", entity.getCpf());
        assertEquals(LocalDate.of(2000,1,1), entity.getDataNascimento());
        assertEquals("a@b.com", entity.getEmail());
        assertEquals("41999999999", entity.getTelefone());

        PessoaBO bo2 = PessoaMapper.toBO(entity);
        assertNotNull(bo2);
        assertEquals(1, bo2.getId());
        assertEquals("Fulano", bo2.getNomeCompleto());
        assertEquals("12345678910", bo2.getCpf());
        assertEquals(LocalDate.of(2000,1,1), bo2.getDataNascimento());
        assertEquals("a@b.com", bo2.getEmail());
        assertEquals("41999999999", bo2.getTelefone());
    }
}
