package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.mapper;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PessoaBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.dto.PessoaDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PessoaDTOMapperTest {

    @Test
    void toBo_e_toDto_devemMapearTodosOsCampos() {
        PessoaDTO dto = new PessoaDTO();
        dto.setId(1);
        dto.setNomeCompleto("Fulano");
        dto.setCpf("12345678910");
        dto.setDataNascimento(LocalDate.of(2000,1,1));
        dto.setEmail("a@b.com");
        dto.setTelefone("41999999999");

        PessoaBO bo = PessoaDTOMapper.toBo(dto);
        assertNotNull(bo);
        assertEquals(1, bo.getId());
        assertEquals("Fulano", bo.getNomeCompleto());
        assertEquals("12345678910", bo.getCpf());
        assertEquals(LocalDate.of(2000,1,1), bo.getDataNascimento());
        assertEquals("a@b.com", bo.getEmail());
        assertEquals("41999999999", bo.getTelefone());

        PessoaDTO dto2 = PessoaDTOMapper.toDto(bo);
        assertNotNull(dto2);
        assertEquals(1, dto2.getId());
        assertEquals("Fulano", dto2.getNomeCompleto());
        assertEquals("12345678910", dto2.getCpf());
        assertEquals(LocalDate.of(2000,1,1), dto2.getDataNascimento());
        assertEquals("a@b.com", dto2.getEmail());
        assertEquals("41999999999", dto2.getTelefone());
    }
}
