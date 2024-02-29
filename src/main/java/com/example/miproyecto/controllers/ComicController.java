package com.example.miproyecto.controllers;

import com.example.miproyecto.entities.Comic;
import com.example.miproyecto.services.ComicService;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/comics")
@Tag(name = "Comics", description = "Controlador De Cómics, métodos CRUD y consultas propias")
public class ComicController {

    @Autowired
    private ComicService comicService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar cómics.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Comic.class)))
            })
    })
    @Operation(summary = "Obtener la lista de cómics", description = "Devuelve una lista con todos los cómics.")
    public List<Comic> list(){
        return comicService.findAll();
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar cómics.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Comic.class)))
            })
    })
    @Operation(summary = "Obtener la lista de cómics referenciado por ID", description = "Devuelve una lista con todos los cómics referenciado por ID.")
    public ResponseEntity<Comic> view(@PathVariable Long id){
        Optional<Comic> comicOptional = comicService.findById(id);
        if(comicOptional.isPresent()){
            return ResponseEntity.ok(comicOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    /////////////////////////////////////////////////
    @GetMapping("/autor/{name}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar cómics por autor.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Comic.class)))
            })
    })
    @Operation(summary = "Obtener la lista de cómics por autor", description = "Devuelve una lista con todos los cómics que contienen el nombre del autor especificado.")
    public List<Comic> findByAutor(@PathVariable String name) {
        return comicService.findByAutorContains(name);
    }
    /////////////////////////////////////////////////

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inserta Cómic.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Comic.class)))
            })
    })
    @Operation(summary = "Insertar Cómics", description = "Inserta cómics en la base de datos.")
    public ResponseEntity<?> create(@Valid @RequestBody Comic comic, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(comicService.save(comic));
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actualización de los cómics.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Comic.class)))
            })
    })
    @Operation(summary = "Actualización de los cómics", description = "Actualizar un cómic en función a la id referenciada.")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Comic comic, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        Optional <Comic> comicOptional = comicService.update(id, comic);
        if(comicOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(comicOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Borrar cómics.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Comic.class)))
            })
    })
    @Operation(summary = "Borrado de cómic", description = "Borrar un cómic concreto que se encuentra en la base de datos.")
    public ResponseEntity<Comic> delete(@PathVariable Long id){
        Optional<Comic> comicOptional = comicService.delete(id);
        if(comicOptional.isPresent()){
            return ResponseEntity.ok(comicOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<String, String>(){};

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
