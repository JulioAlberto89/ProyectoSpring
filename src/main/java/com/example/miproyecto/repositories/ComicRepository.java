package com.example.miproyecto.repositories;

import com.example.miproyecto.entities.Comic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComicRepository extends JpaRepository<Comic, Long> {
    List<Comic> findByAutorContains(String name);
}
