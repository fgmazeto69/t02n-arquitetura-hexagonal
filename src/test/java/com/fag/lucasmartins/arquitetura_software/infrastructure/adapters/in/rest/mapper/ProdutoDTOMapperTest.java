package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.mapper;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.dto.ProdutoDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoDTOMapperTest {

    @Test
    void toBo_e_toDto_devemMapearTodosOsCampos() {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(1);
        dto.setNome("Nome");
        dto.setPreco(10.5);
        dto.setEstoque(7);

        ProdutoBO bo = ProdutoDTOMapper.toBo(dto);
        assertNotNull(bo);
        assertEquals(1, bo.getId());
        assertEquals("Nome", bo.getNome());
        assertEquals(10.5, bo.getPreco(), 0.0001);
        assertEquals(7, bo.getEstoque());

        ProdutoDTO dto2 = ProdutoDTOMapper.toDto(bo);
        assertNotNull(dto2);
        assertEquals(1, dto2.getId());
        assertEquals("Nome", dto2.getNome());
        assertEquals(10.5, dto2.getPreco(), 0.0001);
        assertEquals(7, dto2.getEstoque());
    }
}
