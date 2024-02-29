package com.example.miproyecto.controllers;

import com.example.miproyecto.entities.Usuario;
import com.example.miproyecto.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Controlador de Usuarios, métodos CRUD y consultas propias")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Insertar usuario.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Usuario.class)))
            })
    })
    @Operation(summary = "Insertar Usuarios", description = "Inserta un usuario concreto.")
    public List<Usuario> list(){
        return usuarioService.findAll();
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Edita Usuario.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Usuario.class)))
            })
    })
    @Operation(summary = "Editar Usuarios", description = "Editar usuarios en la base de datos.")
    public ResponseEntity<?> create(@Valid @RequestBody Usuario usuario, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
    }

    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro de Usuario.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Usuario.class)))
            })
    })
    @Operation(summary = "Registro de Usuarios", description = "Registro de usuarios en la base de datos.")
    public ResponseEntity<?> register(@Valid @RequestBody Usuario usuario, BindingResult result){
        usuario.setAdministrador(false);
        return create(usuario, result);
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<String, String>(){};

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
