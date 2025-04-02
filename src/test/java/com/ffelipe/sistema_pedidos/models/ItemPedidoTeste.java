package com.ffelipe.sistema_pedidos.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ItemPedidoTest {

    private Produto produto;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Pizza");
        
        produto.setPreco(new BigDecimal("40.00"));

        pedido = new Pedido();
    }

    @Test
    void itemPedidoDeveTerProdutoEQuantidadeValida() {
        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(2);

        assertEquals(new BigDecimal("80.00"), item.calcularSubtotal());
    }

    @Test
    void itemPedidoSemProdutoDeveLancarExcecao() {
        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setQuantidade(2);

        IllegalStateException exception = assertThrows(IllegalStateException.class, item::calcularSubtotal);
        assertEquals("ItemPedido deve estar associado a um produto e ter quantidade válida.", exception.getMessage());
    }

    @Test
    void itemPedidoComQuantidadeZeroDeveLancarExcecao() {
        ItemPedido item = new ItemPedido();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(0);

        IllegalStateException exception = assertThrows(IllegalStateException.class, item::calcularSubtotal);
        assertEquals("ItemPedido deve estar associado a um produto e ter quantidade válida.", exception.getMessage());
    }
}