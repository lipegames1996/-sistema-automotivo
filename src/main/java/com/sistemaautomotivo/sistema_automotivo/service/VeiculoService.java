package com.sistemaautomotivo.sistema_automotivo.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sistemaautomotivo.sistema_automotivo.model.Marca;
import com.sistemaautomotivo.sistema_automotivo.model.Modelo;
import com.sistemaautomotivo.sistema_automotivo.model.Veiculo;
import com.sistemaautomotivo.sistema_automotivo.repository.MarcaRepository;
import com.sistemaautomotivo.sistema_automotivo.repository.ModeloRepository;
import com.sistemaautomotivo.sistema_automotivo.repository.VeiculoRepository;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final MarcaRepository marcaRepository;
    private final ModeloRepository modeloRepository;

    public VeiculoService(
            VeiculoRepository veiculoRepository,
            MarcaRepository marcaRepository,
            ModeloRepository modeloRepository) {

        this.veiculoRepository = veiculoRepository;
        this.marcaRepository = marcaRepository;
        this.modeloRepository = modeloRepository;
    }

    // Cadastrar veículo
    public Veiculo cadastrarVeiculo(Veiculo veiculo) {

        veiculo.setId(null);

        validarVeiculo(veiculo);
        associarModelo(veiculo);

        return veiculoRepository.save(veiculo);
    }

    // Listar veículos com filtros opcionais
    public List<Veiculo> listarVeiculos(
            String marca,
            String modelo,
            Integer ano,
            Boolean disponivel,
            BigDecimal precoMinimo,
            BigDecimal precoMaximo) {

        if (precoMinimo != null
                && precoMaximo != null
                && precoMinimo.compareTo(precoMaximo) > 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O preço mínimo não pode ser maior que o máximo."
            );
        }

        Specification<Veiculo> filtros =
                (root, query, cb) -> cb.conjunction();

        if (marca != null && !marca.isBlank()) {

            String marcaFiltro = marca.trim().toLowerCase(Locale.ROOT);

            filtros = filtros.and((root, query, cb) ->
                    cb.equal(
                            cb.lower(root.get("marca")),
                            marcaFiltro
                    ));
        }

        if (modelo != null && !modelo.isBlank()) {

            String modeloFiltro = modelo.trim().toLowerCase(Locale.ROOT);

            filtros = filtros.and((root, query, cb) ->
                    cb.equal(
                            cb.lower(root.get("modelo")),
                            modeloFiltro
                    ));
        }

        if (ano != null) {

            filtros = filtros.and((root, query, cb) ->
                    cb.equal(root.get("ano"), ano));
        }

        if (disponivel != null) {

            filtros = filtros.and((root, query, cb) ->
                    cb.equal(root.get("disponivel"), disponivel));
        }

        if (precoMinimo != null) {

            filtros = filtros.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(
                            root.get("preco"),
                            precoMinimo
                    ));
        }

        if (precoMaximo != null) {

            filtros = filtros.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(
                            root.get("preco"),
                            precoMaximo
                    ));
        }

        return veiculoRepository.findAll(filtros);
    }

    // Listar todos os veículos
    public List<Veiculo> listarVeiculos() {
        return veiculoRepository.findAll();
    }

    // Buscar veículo pelo ID
    public Veiculo buscarVeiculoPorId(Long id) {

        return veiculoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Veículo não encontrado."
                ));
    }

    // Atualizar veículo
    public Veiculo atualizarVeiculo(Long id, Veiculo dados) {

        Veiculo veiculo = buscarVeiculoPorId(id);

        validarVeiculo(dados);
        associarModelo(dados);

        veiculo.setMarca(dados.getMarca());
        veiculo.setModelo(dados.getModelo());
        veiculo.setAno(dados.getAno());
        veiculo.setCor(dados.getCor());
        veiculo.setPreco(dados.getPreco());
        veiculo.setQuilometragem(dados.getQuilometragem());
        veiculo.setDisponivel(dados.getDisponivel());
        veiculo.setModeloAssociado(dados.getModeloAssociado());

        return veiculoRepository.save(veiculo);
    }

    // Excluir veículo
    public void excluirVeiculo(Long id) {

        Veiculo veiculo = buscarVeiculoPorId(id);

        veiculoRepository.delete(veiculo);
    }

    // Associar veículo à marca e ao modelo cadastrados
    private void associarModelo(Veiculo veiculo) {

        Marca marca = marcaRepository
                .findByNomeIgnoreCase(veiculo.getMarca())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Marca não cadastrada. Cadastre a marca primeiro."
                ));

        Modelo modelo = modeloRepository
                .findByNomeIgnoreCaseAndMarcaId(
                        veiculo.getModelo(),
                        marca.getId()
                )
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Modelo não cadastrado para esta marca."
                ));

        veiculo.setMarca(marca.getNome());
        veiculo.setModelo(modelo.getNome());
        veiculo.setModeloAssociado(modelo);
    }

    // Validar informações do veículo
    private void validarVeiculo(Veiculo veiculo) {

        if (veiculo.getMarca() == null
                || veiculo.getMarca().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A marca é obrigatória."
            );
        }

        if (veiculo.getModelo() == null
                || veiculo.getModelo().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O modelo é obrigatório."
            );
        }

        if (veiculo.getAno() == null
                || veiculo.getAno() < 1886) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Informe um ano válido."
            );
        }

        if (veiculo.getPreco() == null
                || veiculo.getPreco().compareTo(BigDecimal.ZERO) < 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Informe um preço válido."
            );
        }

        if (veiculo.getQuilometragem() != null
                && veiculo.getQuilometragem() < 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A quilometragem não pode ser negativa."
            );
        }

        if (veiculo.getDisponivel() == null) {
            veiculo.setDisponivel(true);
        }
    }
}