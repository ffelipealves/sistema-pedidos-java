package com.ffelipe.sistema_pedidos.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffelipe.sistema_pedidos.models.ItemPedido;
import com.ffelipe.sistema_pedidos.models.Pedido;
import com.ffelipe.sistema_pedidos.models.Produto;
import com.ffelipe.sistema_pedidos.repository.PedidoRepository;
import com.ffelipe.sistema_pedidos.repository.ProdutoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Pedido criarPedido(Pedido pedido) {
        if (pedido.getItens().isEmpty()) {
            throw new IllegalArgumentException("O pedido deve ter pelo menos um item.");
        }
    
        // Garantir que cada item do pedido tem um produto válido
        for (ItemPedido item : pedido.getItens()) {
            Produto produto = produtoRepository.findById(item.getProduto().getId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com ID: " + item.getProduto().getId()));
    
            item.setProduto(produto);
            item.setPedido(pedido); // Associar o pedido ao item
        }
    
        pedido.calcularValorTotal(); // O próprio Pedido já cuida do cálculo do total
        return pedidoRepository.save(pedido);
    }
}


