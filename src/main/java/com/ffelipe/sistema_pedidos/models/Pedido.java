package com.ffelipe.sistema_pedidos.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valorTotal = BigDecimal.ZERO;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();

    public void calcularValorTotal() {
        if (itens.isEmpty()) {
            throw new IllegalStateException("Pedido precisa ter pelo menos 1 item.");
        }

        this.valorTotal = itens.stream()
            .map(ItemPedido::calcularSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void adicionarItem(ItemPedido item) {
        if (item == null) {
            throw new IllegalArgumentException("O item não pode ser nulo.");
        }
        item.setPedido(this); // Garante que o item pertence a este pedido
        this.itens.add(item);
        calcularValorTotal();
    }

    public void removerItem(ItemPedido item) {
        if (item == null || !this.itens.contains(item)) {
            throw new IllegalArgumentException("Item inválido para remoção.");
        }
        this.itens.remove(item);
        calcularValorTotal();
    }

    @PrePersist
    @PreUpdate
    private void validarPedido() {
        if (itens.isEmpty()) {
            throw new IllegalStateException("Pedido deve ter pelo menos 1 item.");
        }
        calcularValorTotal();
    }
}

