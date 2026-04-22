package com.fag.lucasmartins.arquitetura_software.core.domain.bo;

import com.fag.lucasmartins.arquitetura_software.core.domain.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PedidoBOTest {

    @Test
    void validarCamposObrigatorios_deveValidarPessoaCepEItens() {
        PedidoBO pedido = new PedidoBO();
        DomainException ex1 = assertThrows(DomainException.class, pedido::validarCamposObrigatorios);
        assertTrue(ex1.getMessage().contains("Pessoa"));

        pedido.setPessoa(new PessoaBO());
        DomainException ex2 = assertThrows(DomainException.class, pedido::validarCamposObrigatorios);
        assertTrue(ex2.getMessage().contains("CEP"));

        pedido.setCep("80000-000");
        DomainException ex3 = assertThrows(DomainException.class, pedido::validarCamposObrigatorios);
        assertTrue(ex3.getMessage().contains("ao menos um item"));

        PedidoProdutoBO item = new PedidoProdutoBO();
        item.setProduto(new ProdutoBO());
        item.setQuantidade(1);
        pedido.setItens(Arrays.asList(item));

        assertDoesNotThrow(pedido::validarCamposObrigatorios);
    }

    @Test
    void normalizarCep_removeNaoNumericos() {
        PedidoBO pedido = new PedidoBO();
        pedido.setCep("80.000-000");
        pedido.normalizarCep();
        assertEquals("80000000", pedido.getCep());
    }

    @Test
    void validarCep_deveLancarQuandoDiferenteDe8() {
        PedidoBO pedido = new PedidoBO();
        pedido.setCep("123");
        DomainException ex = assertThrows(DomainException.class, pedido::validarCep);
        assertTrue(ex.getMessage().contains("8 dígitos"));
    }

    @Test
    void calcularValorTotal_somaSubtotais() {
        ProdutoBO p = new ProdutoBO();
        p.setPreco(10.0);
        PedidoProdutoBO i1 = new PedidoProdutoBO();
        i1.setProduto(p);
        i1.setQuantidade(2);
        i1.calcularSubtotal();
        PedidoProdutoBO i2 = new PedidoProdutoBO();
        i2.setProduto(p);
        i2.setQuantidade(3);
        i2.calcularSubtotal();

        PedidoBO pedido = new PedidoBO();
        pedido.setItens(Arrays.asList(i1, i2));

        pedido.calcularValorTotal();

        assertEquals(50.0, pedido.getValorTotal(), 0.0001);
    }
}
