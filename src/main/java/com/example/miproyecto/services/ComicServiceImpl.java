package com.example.miproyecto.services;

import com.example.miproyecto.entities.Comic;
import com.example.miproyecto.repositories.ComicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ComicServiceImpl implements ComicService{

    @Autowired
    private ComicRepository comicRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Comic> findAll() {
        return  comicRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Comic> findByAutorContains(String name) {
        return comicRepository.findByAutorContains(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comic> findById(Long id) {
        return comicRepository.findById(id);
    }

    @Override
    @Transactional
    public Comic save(Comic comic) {
        return comicRepository.save(comic);
    }

    @Override
    @Transactional
    public Optional <Comic> update(Long id, Comic comic) {
        Optional <Comic> comicOptional = comicRepository.findById(id);
        if(comicOptional.isPresent()){
            Comic comicDb = comicOptional.orElseThrow();
            comicDb.setTitulo(comic.getTitulo());
            comicDb.setPrecio(comic.getPrecio());
            comicDb.setAutor(comic.getAutor());
            comicDb.setIsbn(comic.getIsbn());
            comicDb.setEditorial(comic.getEditorial());
            comicDb.setFecha_lanzamiento(comic.getFecha_lanzamiento());
            comicDb.setGenero(comic.getGenero());
            comicDb.setFormato(comic.getFormato());
            return Optional.of(comicRepository.save(comicDb));
        }
        return comicOptional;
    }

    @Override
    @Transactional
    public Optional<Comic> delete(Long id) {
        Optional <Comic> comicOptional = comicRepository.findById(id);
        comicOptional.ifPresent( comicDb -> comicRepository.delete(comicDb));
        return comicOptional;
    }
}
