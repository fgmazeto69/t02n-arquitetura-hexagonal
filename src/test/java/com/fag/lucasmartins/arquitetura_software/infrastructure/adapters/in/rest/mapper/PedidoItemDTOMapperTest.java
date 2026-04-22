package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.mapper;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoProdutoBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.dto.PedidoProdutoDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.dto.ProdutoDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoItemDTOMapperTest {

    @Test
    void toBO_e_toDTO_devemMapearTodosOsCampos() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(10);
        produtoDTO.setNome("P");
        produtoDTO.setPreco(5.5);
        produtoDTO.setEstoque(3);

        PedidoProdutoDTO dto = new PedidoProdutoDTO();
        dto.setId(1);
        dto.setProduto(produtoDTO);
        dto.setQuantidade(2);

        PedidoProdutoBO bo = PedidoItemDTOMapper.toBO(dto);
        assertNotNull(bo);
        assertEquals(1, bo.getId());
        assertEquals(2, bo.getQuantidade());
        assertNotNull(bo.getProduto());
        assertEquals(10, bo.getProduto().getId());

        bo.setSubtotal(11.0);
        PedidoProdutoDTO dto2 = PedidoItemDTOMapper.toDTO(bo);
        assertNotNull(dto2);
        assertEquals(1, dto2.getId());
        assertEquals(2, dto2.getQuantidade());
        assertEquals(11.0, dto2.getSubtotal(), 0.0001);
        assertNotNull(dto2.getProduto());
        assertEquals(10, dto2.getProduto().getId());
    }
}
