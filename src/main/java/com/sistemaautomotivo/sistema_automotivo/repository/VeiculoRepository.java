package com.sistemaautomotivo.sistema_automotivo.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sistemaautomotivo.sistema_automotivo.model.Veiculo;

public interface VeiculoRepository extends
        JpaRepository<Veiculo, Long>,
        JpaSpecificationExecutor<Veiculo> {

    List<Veiculo> findByMarcaIgnoreCase(String marca);

    List<Veiculo> findByModeloIgnoreCase(String modelo);

    List<Veiculo> findByAno(Integer ano);

    List<Veiculo> findByDisponivel(Boolean disponivel);

    List<Veiculo> findByPrecoBetween(BigDecimal precoMinimo, BigDecimal precoMaximo);

}