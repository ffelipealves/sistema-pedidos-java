package com.ffelipe.sistema_pedidos.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffelipe.sistema_pedidos.dto.ItemPedidoDTO;
import com.ffelipe.sistema_pedidos.dto.PedidoDTO;
import com.ffelipe.sistema_pedidos.mapper.PedidoMapper;
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

    public PedidoDTO criarPedido(PedidoDTO pedidoDTO) {
        // Buscar todos os produtos referenciados no pedido
        List<Long> idsProdutos = pedidoDTO.itens().stream()
            .map(ItemPedidoDTO::produtoId)
            .distinct()
            .toList();

        List<Produto> produtos = produtoRepository.findAllById(idsProdutos);

        // Converter DTO para entidade
        Pedido pedido = PedidoMapper.toEntity(pedidoDTO, produtos);

        // Salvar no banco
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        // Retornar como DTO de volta
        return PedidoMapper.toDTO(pedidoSalvo);
    }

    public List<PedidoDTO> listarPedidos() {
        return pedidoRepository.findAll()
            .stream()
            .map(PedidoMapper::toDTO)
            .toList();
    }

    public Optional<PedidoDTO> buscarPorId(Long id) {
        return pedidoRepository.findById(id)
            .map(PedidoMapper::toDTO);
    }

    public boolean deletar(Long id) {
        if (!pedidoRepository.existsById(id)) {
            return false;
        }
        pedidoRepository.deleteById(id);
        return true;
    }
}



