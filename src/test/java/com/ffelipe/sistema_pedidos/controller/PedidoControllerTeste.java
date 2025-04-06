package com.ffelipe.sistema_pedidos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffelipe.sistema_pedidos.dto.ItemPedidoDTO;
import com.ffelipe.sistema_pedidos.dto.PedidoDTO;
import com.ffelipe.sistema_pedidos.service.PedidoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.hasSize;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoController.class)
class PedidoControllerTeste {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarPedidoComSucesso() throws Exception {
        ItemPedidoDTO item = new ItemPedidoDTO(1L, "Hamburguer", 2, new BigDecimal("10.00"), new BigDecimal("20.00"));
        PedidoDTO dtoEntrada = new PedidoDTO(null, new BigDecimal("20.00"), List.of(item));
        PedidoDTO dtoRetornado = new PedidoDTO(1L, new BigDecimal("20.00"), List.of(item));

        Mockito.when(pedidoService.criarPedido(any())).thenReturn(dtoRetornado);

        mockMvc.perform(post("/api/pedidos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dtoEntrada)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.valorTotal").value(20.00))
                .andExpect(jsonPath("$.itens", hasSize(1)))
                .andExpect(jsonPath("$.itens[0].nomeProduto").value("Hamburguer"));
    }

    @Test
    void deveListarTodosOsPedidos() throws Exception {
        ItemPedidoDTO item = new ItemPedidoDTO(1L, "Hamburguer", 2, new BigDecimal("10.00"), new BigDecimal("20.00"));
        PedidoDTO pedido = new PedidoDTO(1L, new BigDecimal("20.00"), List.of(item));

        Mockito.when(pedidoService.listarPedidos()).thenReturn(List.of(pedido));

        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void deveBuscarPedidoPorId() throws Exception {
        ItemPedidoDTO item = new ItemPedidoDTO(1L, "Hamburguer", 2, new BigDecimal("10.00"), new BigDecimal("20.00"));
        PedidoDTO pedido = new PedidoDTO(1L, new BigDecimal("20.00"), List.of(item));

        Mockito.when(pedidoService.buscarPorId(1L)).thenReturn(Optional.of(pedido));

        mockMvc.perform(get("/api/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deveRetornar404SePedidoNaoExistir() throws Exception {
        Mockito.when(pedidoService.buscarPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/pedidos/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveDeletarPedidoComSucesso() throws Exception {
        Mockito.when(pedidoService.deletar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/pedidos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deveRetornar404AoDeletarPedidoInexistente() throws Exception {
        Mockito.when(pedidoService.deletar(999L)).thenReturn(false);

        mockMvc.perform(delete("/api/pedidos/999"))
                .andExpect(status().isNotFound());
    }
}