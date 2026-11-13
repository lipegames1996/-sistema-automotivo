package com.sistemaautomotivo.sistema_automotivo.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sistemaautomotivo.sistema_automotivo.model.Marca;
import com.sistemaautomotivo.sistema_automotivo.repository.MarcaRepository;

@Service
public class MarcaService {

    private final MarcaRepository marcaRepository;

    public MarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    // Cadastrar marca
    public Marca cadastrarMarca(Marca marca) {

        validarNome(marca.getNome());

        if (marcaRepository.existsByNomeIgnoreCase(marca.getNome())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esta marca já está cadastrada."
            );
        }

        marca.setId(null);

        return marcaRepository.save(marca);
    }

    // Listar todas as marcas
    public List<Marca> listarMarcas() {
        return marcaRepository.findAll();
    }

    // Buscar marca pelo ID
    public Marca buscarMarcaPorId(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Marca não encontrada."
                ));
    }

    // Atualizar marca
    public Marca atualizarMarca(Long id, Marca dados) {

        Marca marca = buscarMarcaPorId(id);

        validarNome(dados.getNome());

        marcaRepository.findByNomeIgnoreCase(dados.getNome())
                .ifPresent(marcaExistente -> {
                    if (!marcaExistente.getId().equals(id)) {
                        throw new ResponseStatusException(
                                HttpStatus.CONFLICT,
                                "Já existe outra marca com este nome."
                        );
                    }
                });

        marca.setNome(dados.getNome());

        return marcaRepository.save(marca);
    }

    // Excluir marca
    public void excluirMarca(Long id) {

        Marca marca = buscarMarcaPorId(id);

        try {
            marcaRepository.delete(marca);
            marcaRepository.flush();
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é possível excluir uma marca associada a modelos.",
                    e
            );
        }
    }

    // Validar nome da marca
    private void validarNome(String nome) {

        if (nome == null || nome.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome da marca é obrigatório."
            );
        }
    }
}