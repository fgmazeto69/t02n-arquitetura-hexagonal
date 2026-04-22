package com.fag.lucasmartins.arquitetura_software.core.domain.bo;

import com.fag.lucasmartins.arquitetura_software.core.domain.exceptions.DomainException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProdutoBOTest {

    @Test
    void validarEstoqueDisponivel_deveLancarExcecao_quandoEstoqueInsuficiente() {
        ProdutoBO bo = new ProdutoBO();
        bo.setEstoque(1);

        DomainException ex = assertThrows(DomainException.class, () -> bo.validarEstoqueDisponivel(2));
        assertTrue(ex.getMessage().contains("Estoque insuficiente"));
    }

    @Test
    void validarEstoqueDisponivel_naoLanca_quandoEstoqueSuficiente() {
        ProdutoBO bo = new ProdutoBO();
        bo.setEstoque(10);
        assertDoesNotThrow(() -> bo.validarEstoqueDisponivel(2));
    }

    @Test
    void validarPrecoProdutoPremium_deveLancar_quandoPremiumEAbaixoDe100() {
        ProdutoBO bo = new ProdutoBO();
        bo.setNome("Cafe Premium");
        bo.setPreco(99.99);
        DomainException ex = assertThrows(DomainException.class, bo::validarPrecoProdutoPremium);
        assertTrue(ex.getMessage().contains("Premium"));
    }

    @Test
    void validarPrecoProdutoPremium_naoLanca_quandoPremiumEAcimaDe100() {
        ProdutoBO bo = new ProdutoBO();
        bo.setNome("Produto PREMIUM 4K");
        bo.setPreco(150.0);
        assertDoesNotThrow(bo::validarPrecoProdutoPremium);
    }

    @Test
    void calcularPrecoFinalPorEstoqueBaixo_defineDescontoDe10PorCento_quandoEstoqueMaiorOuIgual50() {
        ProdutoBO bo = new ProdutoBO();
        bo.setPreco(200.0);
        bo.setEstoque(50);
        bo.calcularPrecoFinalPorEstoqueBaixo();
        assertEquals(180.0, bo.getPrecoFinal(), 0.0001);
    }

    @Test
    void diminuirEstoque_deveSubtrairQuantidade_quandoSuficiente() {
        ProdutoBO bo = new ProdutoBO();
        bo.setEstoque(10);
        bo.diminuirEstoque(3);
        assertEquals(7, bo.getEstoque());
    }

    @Test
    void adicionarEstoque_deveSomarQuantidade() {
        ProdutoBO bo = new ProdutoBO();
        bo.setEstoque(10);
        bo.adicionarEstoque(5);
        assertEquals(15, bo.getEstoque());
    }
}
