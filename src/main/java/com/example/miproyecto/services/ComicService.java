package com.example.miproyecto.services;

import com.example.miproyecto.entities.Comic;

import java.util.List;
import java.util.Optional;

public interface ComicService {
    List<Comic> findAll();
    Optional<Comic> findById(Long id);
    Comic save (Comic comic);
    Optional <Comic> update(Long id, Comic comic);
    Optional<Comic> delete(Long id);

    ///////////////////////////////
    List<Comic> findByAutorContains(String name);
}
