package com.example.miproyecto.services;
import com.example.miproyecto.entities.Usuario;
import com.example.miproyecto.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        Optional<Usuario> userOptional = usuarioRepository.findByNombre(nombre);

        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException((String.format("Nombre %s no existe", nombre)));
        }
        Usuario usuario = userOptional.orElseThrow();

        List<GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getNombre()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(usuario.getNombre(),
                usuario.getPassword(),
                usuario.isEnable(),
                true,
                true,
                true,
                authorities);
    }
}
