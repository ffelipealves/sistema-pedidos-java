package com.ffelipe.sistema_pedidos.mapper;

import com.ffelipe.sistema_pedidos.dto.ItemPedidoDTO;
import com.ffelipe.sistema_pedidos.models.ItemPedido;
import com.ffelipe.sistema_pedidos.models.Produto;

public class ItemPedidoMapper {

    public static ItemPedidoDTO toDTO(ItemPedido item) {
        return new ItemPedidoDTO(
            item.getProduto().getId(),
            item.getProduto().getNome(),
            item.getQuantidade(),
            item.getValorUnitario(),
            item.getSubtotal()
        );
    }

    public static ItemPedido toEntity(ItemPedidoDTO dto, Produto produto) {
        ItemPedido item = new ItemPedido();
        item.setProduto(produto);
        item.setQuantidade(dto.quantidade());
        // `valorUnitario` e `subtotal` são calculados automaticamente pela entity
        return item;
    }
}
