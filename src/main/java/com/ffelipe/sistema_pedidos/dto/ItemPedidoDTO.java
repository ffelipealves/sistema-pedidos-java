package com.ffelipe.sistema_pedidos.dto;

import java.math.BigDecimal;

public record ItemPedidoDTO(
    Long produtoId,
    String nomeProduto,
    int quantidade,
    BigDecimal valorUnitario,
    BigDecimal subtotal
) {}

