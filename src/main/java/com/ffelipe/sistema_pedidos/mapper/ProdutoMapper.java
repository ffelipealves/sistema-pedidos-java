package com.ffelipe.sistema_pedidos.mapper;

import com.ffelipe.sistema_pedidos.dto.ProdutoDTO;
import com.ffelipe.sistema_pedidos.models.Produto;

public class ProdutoMapper {

    public static Produto toEntity(ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setId(dto.id());
        produto.setNome(dto.nome());
        produto.setPreco(dto.preco());
        return produto;
    }

    public static ProdutoDTO toDTO(Produto produto) {
        return new ProdutoDTO(produto.getId(), produto.getNome(), produto.getPreco());
    }
}

