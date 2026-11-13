package com.sistemaautomotivo.sistema_automotivo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemaautomotivo.sistema_automotivo.model.Modelo;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {

    List<Modelo> findByMarcaId(Long marcaId);

    Optional<Modelo> findByNomeIgnoreCaseAndMarcaId(
            String nome,
            Long marcaId
    );

    boolean existsByNomeIgnoreCaseAndMarcaId(
            String nome,
            Long marcaId
    );

}