package com.example.miproyecto.controllers;

import com.example.miproyecto.entities.Calificaciones;
import com.example.miproyecto.entities.Comic;
import com.example.miproyecto.services.CalificacionesService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/calificaciones")
@Tag(name = "Calificaciones", description = "Controlador de las Calificaciones, métodos CRUD y consultas propias")
public class CalificacionesController {

    @Autowired
    private CalificacionesService calificacionesService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar calificaciones.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Calificaciones.class)))
            })
    })
    @Operation(summary = "Obtener la lista de las calificaciones", description = "Devuelve una lista con todos las calificaciones.")
    public List<Calificaciones> list(){
        return calificacionesService.findAll();
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar calificaciones.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Calificaciones.class)))
            })
    })
    @Operation(summary = "Obtener la lista de las calificaciones referenciadas por ID", description = "Devuelve una lista con todos las calificaciones referenciadas por ID.")
    public ResponseEntity<Calificaciones> view(@PathVariable Long id){
        Optional<Calificaciones> calificacionesOptional = calificacionesService.findById(id);
        if(calificacionesOptional.isPresent()){
            return ResponseEntity.ok(calificacionesOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inserta Calificación.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Calificaciones.class)))
            })
    })
    @Operation(summary = "Insertar Calificación", description = "Inserta calificación en la base de datos.")
    public ResponseEntity<?> create(@Valid @RequestBody Calificaciones calificaciones, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(calificacionesService.save(calificaciones));
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actualización de las calificaciones.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Calificaciones.class)))
            })
    })
    @Operation(summary = "Actualización de las calificaciones", description = "Actualizar una calificación en función a la id referenciada.")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Calificaciones calificaciones, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        Optional <Calificaciones> calificacionesOptional = calificacionesService.update(id, calificaciones);
        if(calificacionesOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(calificacionesOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Borrar calificaciones.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Calificaciones.class)))
            })
    })
    @Operation(summary = "Borrado de calificaciones", description = "Borrar una calificación concreta que se encuentra en la base de datos.")
    public ResponseEntity<Calificaciones> delete(@PathVariable Long id){
        Optional<Calificaciones> calificacionesOptional = calificacionesService.delete(id);
        if(calificacionesOptional.isPresent()){
            return ResponseEntity.ok(calificacionesOptional.orElseThrow());
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
