package com.sistemaautomotivo.sistema_automotivo.controller;

import java.math.BigDecimal;
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

import com.sistemaautomotivo.sistema_automotivo.model.Veiculo;
import com.sistemaautomotivo.sistema_automotivo.service.VeiculoService;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    // Cadastrar veículo
    @PostMapping
    public ResponseEntity<Veiculo> cadastrarVeiculo(
            @RequestBody Veiculo veiculo) {

        Veiculo novoVeiculo = veiculoService.cadastrarVeiculo(veiculo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novoVeiculo);
    }

    // Listar veículos com filtros opcionais
    @GetMapping
    public List<Veiculo> listarVeiculos(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) Boolean disponivel,
            @RequestParam(required = false) BigDecimal precoMinimo,
            @RequestParam(required = false) BigDecimal precoMaximo) {

        return veiculoService.listarVeiculos(
                marca,
                modelo,
                ano,
                disponivel,
                precoMinimo,
                precoMaximo
        );
    }

    // Buscar veículo pelo ID
    @GetMapping("/{id}")
    public Veiculo buscarVeiculoPorId(@PathVariable Long id) {

        return veiculoService.buscarVeiculoPorId(id);
    }

    // Atualizar veículo
    @PutMapping("/{id}")
    public Veiculo atualizarVeiculo(
            @PathVariable Long id,
            @RequestBody Veiculo veiculo) {

        return veiculoService.atualizarVeiculo(id, veiculo);
    }

    // Excluir veículo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirVeiculo(@PathVariable Long id) {

        veiculoService.excluirVeiculo(id);

        return ResponseEntity.noContent().build();
    }
}