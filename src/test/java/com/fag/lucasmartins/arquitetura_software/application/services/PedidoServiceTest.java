package com.fag.lucasmartins.arquitetura_software.application.services;

import com.fag.lucasmartins.arquitetura_software.application.ports.out.persistence.PedidoRepositoryPort;
import com.fag.lucasmartins.arquitetura_software.application.ports.out.persistence.PessoaRepositoryPort;
import com.fag.lucasmartins.arquitetura_software.application.ports.out.persistence.ProdutoRepositoryPort;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoProdutoBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PessoaBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.exceptions.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    private PedidoRepositoryPort pedidoRepositoryPort;
    private PessoaRepositoryPort pessoaRepositoryPort;
    private ProdutoRepositoryPort produtoRepositoryPort;
    private PedidoService service;

    @BeforeEach
    void setup() {
        pedidoRepositoryPort = mock(PedidoRepositoryPort.class);
        pessoaRepositoryPort = mock(PessoaRepositoryPort.class);
        produtoRepositoryPort = mock(ProdutoRepositoryPort.class);
        service = new PedidoService(pedidoRepositoryPort, pessoaRepositoryPort, produtoRepositoryPort);
    }

    @Test
    void criarPedido_sucesso_deveValidarNormalizarCalcularDiminuirEstoqueESalvar() {
        // dado um pedido
        PessoaBO pessoa = new PessoaBO();
        pessoa.setId(1);
        ProdutoBO produtoReposit = new ProdutoBO();
        produtoReposit.setId(100);
        produtoReposit.setNome("Produto X");
        produtoReposit.setPreco(10.0);
        produtoReposit.setEstoque(20);

        PedidoProdutoBO item = new PedidoProdutoBO();
        ProdutoBO produtoInformado = new ProdutoBO();
        produtoInformado.setId(100);
        item.setProduto(produtoInformado);
        item.setQuantidade(5);

        PedidoBO pedido = new PedidoBO();
        pedido.setPessoa(pessoa);
        pedido.setCep("80.000-000");
        pedido.setItens(Collections.singletonList(item));

        when(pessoaRepositoryPort.encontrarPorId(1)).thenReturn(pessoa);
        when(produtoRepositoryPort.encontrarPorIds(Collections.singletonList(100)))
                .thenReturn(Collections.singletonList(produtoReposit));
        when(pedidoRepositoryPort.salvar(any())).thenAnswer(inv -> inv.getArgument(0));
        when(produtoRepositoryPort.salvarTodos(any())).thenAnswer(inv -> inv.getArgument(0));

        PedidoBO criado = service.criarPedido(pedido);

        // estoque do produto encontrado deve diminuir 5
        assertEquals(15, produtoReposit.getEstoque());
        // cep deve ser normalizado e total calculado (5 * 10)
        assertEquals("80000000", criado.getCep());
        assertEquals(50.0, criado.getValorTotal(), 0.0001);

        verify(pedidoRepositoryPort).salvar(any());
        verify(produtoRepositoryPort).salvarTodos(any());
    }

    @Test
    void criarPedido_deveLancarQuandoProdutoNaoEncontrado() {
        PessoaBO pessoa = new PessoaBO();
        pessoa.setId(1);
        PedidoProdutoBO item = new PedidoProdutoBO();
        ProdutoBO produtoInformado = new ProdutoBO();
        produtoInformado.setId(999);
        item.setProduto(produtoInformado);
        item.setQuantidade(1);
        PedidoBO pedido = new PedidoBO();
        pedido.setPessoa(pessoa);
        pedido.setCep("80000000");
        pedido.setItens(Collections.singletonList(item));

        when(pessoaRepositoryPort.encontrarPorId(1)).thenReturn(pessoa);
        when(produtoRepositoryPort.encontrarPorIds(Collections.singletonList(999)))
                .thenReturn(Collections.emptyList());

        DomainException ex = assertThrows(DomainException.class, () -> service.criarPedido(pedido));
        assertTrue(ex.getMessage().contains("999"));
        verify(pedidoRepositoryPort, never()).salvar(any());
    }
}
