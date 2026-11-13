package com.sistemaautomotivo.sistema_automotivo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemaautomotivo.sistema_automotivo.model.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Long> {

    Optional<Marca> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);

}