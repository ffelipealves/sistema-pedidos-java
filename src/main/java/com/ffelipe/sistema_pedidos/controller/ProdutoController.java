package com.ffelipe.sistema_pedidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ffelipe.sistema_pedidos.dto.ProdutoDTO;
import com.ffelipe.sistema_pedidos.mapper.ProdutoMapper;
import com.ffelipe.sistema_pedidos.models.Produto;
import com.ffelipe.sistema_pedidos.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    // Listar todos os produtos
    @GetMapping
    public List<ProdutoDTO> listarProdutos() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoMapper::toDTO)
                .toList();
    }

    // Buscar produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarProduto(@PathVariable Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.map(p -> ResponseEntity.ok(ProdutoMapper.toDTO(p)))
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Criar um novo produto
    @PostMapping
    public ResponseEntity<ProdutoDTO> criarProduto(@RequestBody ProdutoDTO dto) {
        Produto novoProduto = produtoRepository.save(ProdutoMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(ProdutoMapper.toDTO(novoProduto));
    }

    // Atualizar um produto existente
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDTO dto) {
        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Produto produto = ProdutoMapper.toEntity(dto);
        produto.setId(id); // garante que atualiza o certo
        Produto atualizado = produtoRepository.save(produto);
        return ResponseEntity.ok(ProdutoMapper.toDTO(atualizado));
    }

    // Deletar um produto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        produtoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


