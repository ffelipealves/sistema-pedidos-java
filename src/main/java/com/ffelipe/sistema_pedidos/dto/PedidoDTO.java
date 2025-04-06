package com.ffelipe.sistema_pedidos.dto;

import java.math.BigDecimal;
import java.util.List;

public record PedidoDTO(
    Long id,
    BigDecimal valorTotal,
    List<ItemPedidoDTO> itens
) {}

