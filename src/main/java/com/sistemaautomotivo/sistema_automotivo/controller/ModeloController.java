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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistemaautomotivo.sistema_automotivo.model.Modelo;
import com.sistemaautomotivo.sistema_automotivo.service.ModeloService;

@RestController
@RequestMapping("/modelos")
public class ModeloController {

    private final ModeloService modeloService;

    public ModeloController(ModeloService modeloService) {
        this.modeloService = modeloService;
    }

    // Cadastrar modelo
    @PostMapping
    public ResponseEntity<Modelo> cadastrarModelo(
            @RequestBody Modelo modelo) {

        Modelo novoModelo = modeloService.cadastrarModelo(modelo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novoModelo);
    }

    // Listar modelos com filtro opcional por marca
    @GetMapping
    public List<Modelo> listarModelos(
            @RequestParam(required = false) Long marcaId) {

        if (marcaId != null) {
            return modeloService.listarModelosPorMarca(marcaId);
        }

        return modeloService.listarModelos();
    }

    // Buscar modelo pelo ID
    @GetMapping("/{id}")
    public Modelo buscarModeloPorId(@PathVariable Long id) {

        return modeloService.buscarModeloPorId(id);
    }

    // Atualizar modelo
    @PutMapping("/{id}")
    public Modelo atualizarModelo(
            @PathVariable Long id,
            @RequestBody Modelo modelo) {

        return modeloService.atualizarModelo(id, modelo);
    }

    // Excluir modelo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirModelo(@PathVariable Long id) {

        modeloService.excluirModelo(id);

        return ResponseEntity.noContent().build();
    }
}