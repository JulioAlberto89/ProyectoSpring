package com.example.miproyecto.services;

import com.example.miproyecto.entities.Comic;
import com.example.miproyecto.entities.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoService {
    List<Pedido> findAll();
    Optional<Pedido> findById(Long id);
    Pedido save (Pedido pedido);
    Optional <Pedido> update(Long id, Pedido pedido);
    Optional<Pedido> delete(Long id);
}
