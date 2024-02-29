package com.example.miproyecto.services;

import com.example.miproyecto.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> findAll();
    Usuario save (Usuario usuario);
}
