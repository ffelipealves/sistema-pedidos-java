package com.ffelipe.sistema_pedidos.dto;

import java.math.BigDecimal;

public record ProdutoDTO(Long id, String nome, BigDecimal preco) {
}

