package com.ffelipe.sistema_pedidos.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    private int quantidade;

    private BigDecimal valorUnitario;
    
    private BigDecimal subtotal;

    public BigDecimal calcularSubtotal() {
        if (produto == null || quantidade <= 0) {
            throw new IllegalStateException("ItemPedido deve estar associado a um produto e ter quantidade válida.");
        }
        return produto.getPreco().multiply(BigDecimal.valueOf(quantidade));
    }

    @PrePersist
    @PreUpdate
    private void validarItemPedido() {
        if (pedido == null || produto == null) {
            throw new IllegalArgumentException("ItemPedido deve estar associado a um Pedido e a um Produto.");
        }
        this.valorUnitario = produto.getPreco();
        this.subtotal = calcularSubtotal();
        pedido.calcularValorTotal(); // Atualiza o valor do pedido
    }
}

