package com.example.miproyecto.controllers;

import com.example.miproyecto.entities.Pedido;
import com.example.miproyecto.services.ComicService;
import com.example.miproyecto.services.PedidoService;
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
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Controlador de los Pedidos, métodos CRUD y consultas propias")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar pedidos.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Pedido.class)))
            })
    })
    @Operation(summary = "Obtener la lista de los pedidos", description = "Devuelve una lista con todos los pedidos.")
    public List<Pedido> list(){
        return pedidoService.findAll();
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Borrar pedidos.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Pedido.class)))
            })
    })
    @Operation(summary = "Listado de los pedidos", description = "Listar un pedido concreto que se encuentra en la base de datos.")
    public ResponseEntity<Pedido> view(@PathVariable Long id){
        Optional<Pedido> pedidoOptional = pedidoService.findById(id);
        if(pedidoOptional.isPresent()){
            return ResponseEntity.ok(pedidoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inserta Pedido.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Pedido.class)))
            })
    })
    @Operation(summary = "Insertar Pedidos", description = "Inserta pedidos en la base de datos.")
    public ResponseEntity<?> create(@Valid @RequestBody Pedido pedido, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.save(pedido));
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actualización de los pedidos.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Pedido.class)))
            })
    })
    @Operation(summary = "Actualización de los pedidos", description = "Actualizar un pedido en función a la id referenciada.")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Pedido pedido, BindingResult result){
        if(result.hasFieldErrors()){
            return validation(result);
        }
        Optional <Pedido> pedidoOptional = pedidoService.update(id, pedido);
        if(pedidoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Borrar pedidos.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Pedido.class)))
            })
    })
    @Operation(summary = "Borrado de los pedidos", description = "Borrar un pedido concreto que se encuentra en la base de datos.")
    public ResponseEntity<Pedido> delete(@PathVariable Long id){
        Optional<Pedido> pedidoOptional = pedidoService.delete(id);
        if(pedidoOptional.isPresent()){
            return ResponseEntity.ok(pedidoOptional.orElseThrow());
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
