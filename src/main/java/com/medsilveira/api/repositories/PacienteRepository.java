package com.medsilveira.api.repositories;

import com.medsilveira.api.entities.Paciente;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

  @Modifying
  @Transactional
  @Query("UPDATE Paciente p SET p.ativo = 0 WHERE p.id = :id")
  void delete(Long id);

  Page<Paciente> findAllByAtivo(Pageable pagination, int i);

  @Query("select count(p) > 0 from Paciente p where p.id = :pacienteId and p.ativo = 1")
  boolean existsByIdAndAtivo(Long pacienteId);
}
