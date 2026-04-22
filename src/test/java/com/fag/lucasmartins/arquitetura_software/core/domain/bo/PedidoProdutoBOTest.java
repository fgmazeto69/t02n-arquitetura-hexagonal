package com.fag.lucasmartins.arquitetura_software.core.domain.bo;

import com.fag.lucasmartins.arquitetura_software.core.domain.exceptions.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoProdutoBOTest {

    @Test
    void validar_deveLancar_quandoSemProduto() {
        PedidoProdutoBO item = new PedidoProdutoBO();
        item.setQuantidade(1);
        DomainException ex = assertThrows(DomainException.class, item::validar);
        assertTrue(ex.getMessage().contains("Produto é obrigatório"));
    }

    @Test
    void validar_deveLancar_quandoQuantidadeMenorIgualZero() {
        PedidoProdutoBO item = new PedidoProdutoBO();
        item.setProduto(new ProdutoBO());
        item.setQuantidade(0);
        DomainException ex = assertThrows(DomainException.class, item::validar);
        assertTrue(ex.getMessage().contains("Quantidade do item"));
    }

    @Test
    void calcularSubtotal_deveMultiplicarPrecoPelaQuantidade() {
        ProdutoBO produto = new ProdutoBO();
        produto.setPreco(25.5);
        PedidoProdutoBO item = new PedidoProdutoBO();
        item.setProduto(produto);
        item.setQuantidade(3);

        item.calcularSubtotal();

        assertEquals(76.5, item.getSubtotal(), 0.0001);
    }
}
