package com.ffelipe.sistema_pedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ffelipe.sistema_pedidos.models.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Você pode adicionar métodos personalizados aqui se necessário
}
