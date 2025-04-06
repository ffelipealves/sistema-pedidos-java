package com.ffelipe.sistema_pedidos.mapper;

import java.util.List;

import com.ffelipe.sistema_pedidos.dto.ItemPedidoDTO;
import com.ffelipe.sistema_pedidos.dto.PedidoDTO;
import com.ffelipe.sistema_pedidos.models.ItemPedido;
import com.ffelipe.sistema_pedidos.models.Pedido;
import com.ffelipe.sistema_pedidos.models.Produto;

public class PedidoMapper {

    public static PedidoDTO toDTO(Pedido pedido) {
        List<ItemPedidoDTO> itensDTO = pedido.getItens()
            .stream()
            .map(ItemPedidoMapper::toDTO)
            .toList();

        return new PedidoDTO(
            pedido.getId(),
            pedido.getValorTotal(),
            itensDTO
        );
    }

    public static Pedido toEntity(PedidoDTO dto, List<Produto> produtos) {
        Pedido pedido = new Pedido();

        List<ItemPedido> itens = dto.itens().stream().map(itemDTO -> {
            Produto produto = produtos.stream()
                .filter(p -> p.getId().equals(itemDTO.produtoId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + itemDTO.produtoId()));
            
            return ItemPedidoMapper.toEntity(itemDTO, produto);
        }).toList();

        // associa os itens ao pedido
        itens.forEach(pedido::adicionarItem);

        return pedido;
    }
}

