package com.example.miproyecto.security;

import com.example.miproyecto.security.filter.JwtAuthenticationFilter;
import com.example.miproyecto.security.filter.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.Security;

@Configuration
public class SpringSecurityConfig {


    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Configuración de los filtros de seguridad

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(h -> h
                        .requestMatchers(HttpMethod.GET, "/api/usuarios").permitAll()// Permitir acceso a estos endpoints sin autenticación
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/register").permitAll()// Permitir acceso a estos endpoints sin autenticación
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").hasRole("ADMIN")
                        /////////////////////Cómics//////////////////////
                        .requestMatchers(HttpMethod.GET, "/api/comics", "/api/comics/{id}").hasAnyRole("ADMIN", "USER")
                        //.requestMatchers(HttpMethod.GET, "/api/comics", "/api/comics/{id}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/comics").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/comics/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/comics/{id}").hasRole("ADMIN")
                        /////////////////////Pedidos//////////////////////
                        .requestMatchers(HttpMethod.GET, "/api/pedidos", "/api/pedidos/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/pedidos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/pedidos/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/pedidos/{id}").hasRole("ADMIN")
                        /////////////////////////calificaciones////////////////////////
                        .requestMatchers(HttpMethod.GET, "/api/calificaciones", "/api/calificaciones/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.POST, "/api/calificaciones").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.PUT, "/api/calificaciones/{id}").hasAnyRole("ADMIN", "USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/calificaciones/{id}").hasAnyRole("ADMIN", "USER")
                        ///////////////////Doc/////////////////
                        .requestMatchers("/swagger-ui/**", "v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated())// Cualquier otra solicitud requiere autenticación
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))// Agregar filtro de autenticación JWT
                .csrf(c -> c.disable())//Desactivar si usas una api Rest
                .sessionManagement(m -> m.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//Desactivar si usas una api Rest
                .build();
    }


}
