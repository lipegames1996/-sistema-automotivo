package com.sistemaautomotivo.sistema_automotivo.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sistemaautomotivo.sistema_automotivo.model.Marca;
import com.sistemaautomotivo.sistema_automotivo.model.Modelo;
import com.sistemaautomotivo.sistema_automotivo.repository.ModeloRepository;

@Service
public class ModeloService {

    private final ModeloRepository modeloRepository;
    private final MarcaService marcaService;

    public ModeloService(
            ModeloRepository modeloRepository,
            MarcaService marcaService) {

        this.modeloRepository = modeloRepository;
        this.marcaService = marcaService;
    }

    // Cadastrar modelo
    public Modelo cadastrarModelo(Modelo modelo) {

        validarNome(modelo.getNome());

        Marca marca = obterMarca(modelo);

        verificarDuplicidade(modelo.getNome(), marca.getId(), null);

        modelo.setId(null);
        modelo.setMarca(marca);

        return modeloRepository.save(modelo);
    }

    // Listar todos os modelos
    public List<Modelo> listarModelos() {
        return modeloRepository.findAll();
    }

    // Listar modelos de uma marca
    public List<Modelo> listarModelosPorMarca(Long marcaId) {

        marcaService.buscarMarcaPorId(marcaId);

        return modeloRepository.findByMarcaId(marcaId);
    }

    // Buscar modelo pelo ID
    public Modelo buscarModeloPorId(Long id) {

        return modeloRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Modelo não encontrado."
                ));
    }

    // Atualizar modelo
    public Modelo atualizarModelo(Long id, Modelo dados) {

        Modelo modelo = buscarModeloPorId(id);

        validarNome(dados.getNome());

        Marca marca = obterMarca(dados);

        verificarDuplicidade(
                dados.getNome(),
                marca.getId(),
                id
        );

        modelo.setNome(dados.getNome());
        modelo.setMarca(marca);

        return modeloRepository.save(modelo);
    }

    // Excluir modelo
    public void excluirModelo(Long id) {

        Modelo modelo = buscarModeloPorId(id);

        try {
            modeloRepository.delete(modelo);
            modeloRepository.flush();

        } catch (DataIntegrityViolationException e) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é possível excluir um modelo associado a veículos.",
                    e
            );
        }
    }

    // Obter uma marca cadastrada
    private Marca obterMarca(Modelo modelo) {

        if (modelo.getMarca() == null
                || modelo.getMarca().getId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Informe o ID da marca do modelo."
            );
        }

        return marcaService.buscarMarcaPorId(
                modelo.getMarca().getId()
        );
    }

    // Impedir modelos duplicados dentro da mesma marca
    private void verificarDuplicidade(
            String nome,
            Long marcaId,
            Long idAtual) {

        modeloRepository
                .findByNomeIgnoreCaseAndMarcaId(nome, marcaId)
                .ifPresent(existente -> {

                    if (idAtual == null
                            || !existente.getId().equals(idAtual)) {

                        throw new ResponseStatusException(
                                HttpStatus.CONFLICT,
                                "Este modelo já está cadastrado para a marca."
                        );
                    }
                });
    }

    // Validar nome do modelo
    private void validarNome(String nome) {

        if (nome == null || nome.isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O nome do modelo é obrigatório."
            );
        }
    }
}