package com.example.miproyecto.services;

import com.example.miproyecto.entities.Calificaciones;
import com.example.miproyecto.repositories.CalificacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public interface CalificacionesService {
    List<Calificaciones> findAll();
    Optional<Calificaciones> findById(Long id);
    Calificaciones save (Calificaciones calificaciones);
    Optional <Calificaciones> update(Long id, Calificaciones calificaciones);
    Optional<Calificaciones> delete(Long id);
}
