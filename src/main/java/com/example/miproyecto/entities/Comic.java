package com.example.miproyecto.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="comic")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long isbn;

    @Column(unique = true)
    @NotBlank
    private String titulo;

    @NotNull
    private String editorial;

    @NotNull
    private String autor;

    private LocalDateTime fecha_lanzamiento;

    private String genero;

    private String formato;

    @Max(value = 500)
    @NotNull
    private Double precio;

    @OneToMany(mappedBy = "comic")
    private List<Calificaciones> calificaciones = new ArrayList<>();

    @OneToMany(mappedBy = "comic")
    private List<Pedido> pedidos = new ArrayList<>();
}