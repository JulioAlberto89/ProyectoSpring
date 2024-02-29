package com.example.miproyecto.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="pedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    @Max(value = 500)
    @NotNull
    private Double precio;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_usuario_pedido"))
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_pedido_comic"))
    private Comic comic;
}
