package com.ffelipe.sistema_pedidos.repository;

import com.ffelipe.sistema_pedidos.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
