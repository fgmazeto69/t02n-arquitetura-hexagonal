package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.mapper;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.entity.ProdutoEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoMapperTest {

    @Test
    void toEntity_e_toBO_deveMapearTodosOsCampos() {
        ProdutoBO bo = new ProdutoBO();
        bo.setId(1);
        bo.setNome("Nome");
        bo.setEstoque(5);
        bo.setPreco(10.5);
        bo.setPrecoFinal(9.0);

        ProdutoEntity entity = ProdutoMapper.toEntity(bo);
        assertNotNull(entity);
        assertEquals(1, entity.getId());
        assertEquals("Nome", entity.getNome());
        assertEquals(5, entity.getEstoque());
        assertEquals(10.5, entity.getPreco(), 0.0001);
        assertEquals(9.0, entity.getPrecoFinal(), 0.0001);

        ProdutoBO bo2 = ProdutoMapper.toBO(entity);
        assertNotNull(bo2);
        assertEquals(1, bo2.getId());
        assertEquals("Nome", bo2.getNome());
        assertEquals(5, bo2.getEstoque());
        assertEquals(10.5, bo2.getPreco(), 0.0001);
        assertEquals(9.0, bo2.getPrecoFinal(), 0.0001);
    }

    @Test
    void toEntity_e_toBO_devemRetornarNullQuandoEntradaNull() {
        assertNull(ProdutoMapper.toEntity(null));
        assertNull(ProdutoMapper.toBO(null));
    }
}
