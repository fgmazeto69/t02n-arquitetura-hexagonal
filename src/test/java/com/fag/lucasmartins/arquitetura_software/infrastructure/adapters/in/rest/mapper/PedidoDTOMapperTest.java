package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.mapper;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoProdutoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.dto.PedidoDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.dto.PedidoProdutoDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.dto.PessoaDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.dto.ProdutoDTO;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PedidoDTOMapperTest {

    @Test
    void toBo_e_toDto_devemMapearCorretamente() {
        // Monta DTO de entrada
        PessoaDTO pessoa = new PessoaDTO();
        pessoa.setId(1);
        pessoa.setNomeCompleto("Fulano");
        pessoa.setCpf("12345678910");
        ProdutoDTO produto = new ProdutoDTO();
        produto.setId(10);
        produto.setNome("P");
        produto.setPreco(5.0);
        produto.setEstoque(10);
        PedidoProdutoDTO item = new PedidoProdutoDTO();
        item.setId(100);
        item.setProduto(produto);
        item.setQuantidade(2);
        PedidoDTO dto = new PedidoDTO();
        dto.setId(UUID.randomUUID());
        dto.setPessoa(pessoa);
        dto.setCep("80000-000");
        dto.setItens(Collections.singletonList(item));

        // Converte pra BO
        PedidoBO bo = PedidoDTOMapper.toBo(dto);
        assertNotNull(bo.getPessoa());
        assertEquals("80000-000", bo.getCep());
        assertEquals(1, bo.getItens().size());
        PedidoProdutoBO itemBO = bo.getItens().get(0);
        assertEquals(2, itemBO.getQuantidade());
        assertNotNull(itemBO.getProduto());
        assertEquals(10, itemBO.getProduto().getId());

        // Ajusta valores para retorno
        itemBO.setId(100);
        itemBO.setSubtotal(10.0);
        bo.setValorTotal(10.0);

        // Converte pra DTO de resposta
        PedidoDTO dtoResp = PedidoDTOMapper.toDto(bo);
        assertEquals("80000-000", dtoResp.getCep());
        assertEquals(1, dtoResp.getItens().size());
        assertEquals(10.0, dtoResp.getValorTotal());
        assertEquals(100, dtoResp.getItens().get(0).getId());
        assertEquals(10.0, dtoResp.getItens().get(0).getSubtotal());
    }
}
