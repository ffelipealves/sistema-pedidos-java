package com.ffelipe.sistema_pedidos.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private Pedido pedido;
    private Produto produto;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        produto = new Produto(1L, "Hamburguer", new BigDecimal("10.00"));
    }

    @Test
    void pedidoDeveTerAoMenosUmItem() {
        assertThrows(IllegalStateException.class, () -> pedido.calcularValorTotal());
    }

    @Test
    void deveCalcularValorTotalCorretamente() {
        ItemPedido item = new ItemPedido(null, pedido, produto, 2, produto.getPreco(), BigDecimal.ZERO);
        pedido.getItens().add(item);
        pedido.calcularValorTotal();
        assertEquals(new BigDecimal("20.00"), pedido.getValorTotal());
    }
}
