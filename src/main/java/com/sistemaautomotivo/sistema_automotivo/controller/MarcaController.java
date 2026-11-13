package com.sistemaautomotivo.sistema_automotivo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaautomotivo.sistema_automotivo.model.Marca;
import com.sistemaautomotivo.sistema_automotivo.service.MarcaService;

@RestController
@RequestMapping("/marcas")
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    // Cadastrar marca
    @PostMapping
    public ResponseEntity<Marca> cadastrarMarca(
            @RequestBody Marca marca) {

        Marca novaMarca = marcaService.cadastrarMarca(marca);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novaMarca);
    }

    // Listar todas as marcas
    @GetMapping
    public List<Marca> listarMarcas() {
        return marcaService.listarMarcas();
    }

    // Buscar marca pelo ID
    @GetMapping("/{id}")
    public Marca buscarMarcaPorId(@PathVariable Long id) {
        return marcaService.buscarMarcaPorId(id);
    }

    // Atualizar marca
    @PutMapping("/{id}")
    public Marca atualizarMarca(
            @PathVariable Long id,
            @RequestBody Marca marca) {

        return marcaService.atualizarMarca(id, marca);
    }

    // Excluir marca
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirMarca(@PathVariable Long id) {

        marcaService.excluirMarca(id);

        return ResponseEntity.noContent().build();
    }
}