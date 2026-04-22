package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.mapper;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoProdutoBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.entity.PedidoProdutoEntity;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.entity.ProdutoEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoItemMapperTest {

    @Test
    void toEntity_deveMapearTodosOsCampos() {
        ProdutoBO p = new ProdutoBO();
        p.setId(9);
        p.setNome("N");
        p.setPreco(2.5);
        p.setEstoque(3);

        PedidoProdutoBO bo = new PedidoProdutoBO();
        bo.setId(1);
        bo.setProduto(p);
        bo.setQuantidade(4);
        bo.setSubtotal(10.0);

        PedidoProdutoEntity entity = PedidoItemMapper.toEntity(bo);
        assertEquals(1, entity.getId());
        assertNotNull(entity.getProduto());
        assertEquals(9, entity.getProduto().getId());
        assertEquals(4, entity.getQuantidade());
        assertEquals(10.0, entity.getSubtotal(), 0.0001);
    }

    @Test
    void toBO_deveMapearTodosOsCampos() {
        ProdutoEntity pe = new ProdutoEntity();
        pe.setId(7);
        pe.setNome("X");
        pe.setPreco(1.0);
        pe.setEstoque(2);

        PedidoProdutoEntity entity = new PedidoProdutoEntity();
        entity.setId(2);
        entity.setProduto(pe);
        entity.setQuantidade(5);
        entity.setSubtotal(5.0);

        PedidoProdutoBO bo = PedidoItemMapper.toBO(entity);
        assertEquals(2, bo.getId());
        assertNotNull(bo.getProduto());
        assertEquals(7, bo.getProduto().getId());
        assertEquals(5, bo.getQuantidade());
        assertEquals(5.0, bo.getSubtotal(), 0.0001);
    }
}
