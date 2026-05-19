package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.mapper;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoProdutoBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PessoaBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedido.dto.PedidoEventDTO;

import java.util.ArrayList;
import java.util.List;

public class PedidoEventMapper {

    private PedidoEventMapper() {
    }

    public static PedidoBO toBo(PedidoEventDTO evento) {
        final PedidoBO pedido = new PedidoBO();

        pedido.setCep(evento.getZipCode());
        pedido.setPessoa(toPessoaBO(evento.getCustomerId()));
        pedido.setItens(toItensBO(evento.getOrderItems()));

        return pedido;
    }

    private static PessoaBO toPessoaBO(Long customerId) {
        if (customerId == null) {
            return null;
        }
        final PessoaBO pessoaBO = new PessoaBO();
        pessoaBO.setId(Math.toIntExact(customerId));
        return pessoaBO;
    }

    private static List<PedidoProdutoBO> toItensBO(List<PedidoEventDTO.PedidoItemEventDTO> orderItems) {
        final List<PedidoProdutoBO> itens = new ArrayList<>();
        if (orderItems == null) {
            return itens;
        }

        for (PedidoEventDTO.PedidoItemEventDTO itemEventDTO : orderItems) {
            final ProdutoBO produtoBO = new ProdutoBO();
            produtoBO.setId(itemEventDTO.getSku());

            final PedidoProdutoBO itemBO = new PedidoProdutoBO();
            itemBO.setProduto(produtoBO);
            itemBO.setQuantidade(itemEventDTO.getAmount());

            itens.add(itemBO);
        }
        return itens;
    }
}
