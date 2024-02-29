package com.example.miproyecto.services;

import com.example.miproyecto.entities.Rol;
import com.example.miproyecto.entities.Usuario;
import com.example.miproyecto.repositories.RolRepository;
import com.example.miproyecto.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        Optional<Rol> optionalRoleUser = rolRepository.findByNombre("ROLE_USER");
        List<Rol> roles = new ArrayList<>();
        //optionalRoleUser.ifPresent(r -> roles.add(r));
        optionalRoleUser.ifPresent(roles::add);//Es lo mismo
        if(usuario.isAdministrador()){
            Optional<Rol> optionlRoleAdmin = rolRepository.findByNombre("ROLE_ADMIN");
            optionlRoleAdmin.ifPresent(roles::add);
        }
        usuario.setRoles(roles);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioRepository.save(usuario);
    }
}
