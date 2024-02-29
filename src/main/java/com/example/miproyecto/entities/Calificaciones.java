package com.example.miproyecto.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="calificaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Calificaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Float nota;

    @NotNull
    private String reseña;

    @ManyToOne
    @JoinColumn(name = "id_comic", foreignKey = @ForeignKey(name = "fk_calificacion_comic"))
    private Comic comic;

    @ManyToOne
    @JoinColumn(name = "id_usuario", foreignKey = @ForeignKey(name = "fk_calificaciones_usuario"))
    private Usuario usuario;
}
