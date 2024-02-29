package com.example.miproyecto.repositories;

import com.example.miproyecto.entities.Comic;
import com.example.miproyecto.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
