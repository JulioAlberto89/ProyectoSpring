package com.example.miproyecto.services;

import com.example.miproyecto.entities.Calificaciones;
import com.example.miproyecto.repositories.CalificacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CalificacionesServiceImpl implements CalificacionesService{

    @Autowired
    private CalificacionesRepository calificacionesRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Calificaciones> findAll() {
        return calificacionesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Calificaciones> findById(Long id) {
        return calificacionesRepository.findById(id);
    }

    @Override
    @Transactional
    public Calificaciones save(Calificaciones calificaciones) {
        return calificacionesRepository.save(calificaciones);
    }

    @Override
    @Transactional
    public Optional<Calificaciones> update(Long id, Calificaciones calificaciones) {
        Optional <Calificaciones> calificacionesOptional = calificacionesRepository.findById(id);
        if(calificacionesOptional.isPresent()){
            Calificaciones calificacionesBD = calificacionesOptional.orElseThrow();
            calificacionesBD.setNota(calificaciones.getNota());
            calificacionesBD.setReseña(calificaciones.getReseña());
            return Optional.of(calificacionesRepository.save(calificacionesBD));
        }
        return calificacionesOptional;
    }

    @Override
    @Transactional
    public Optional<Calificaciones> delete(Long id) {
        Optional<Calificaciones> calificacionesOptional = calificacionesRepository.findById(id);
        calificacionesOptional.ifPresent( calificacionesDB -> calificacionesRepository.delete(calificacionesDB));
        return calificacionesOptional;
    }
}
