package com.medsilveira.api.repositories;

import com.medsilveira.api.entities.Medico;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MedicoRepository extends JpaRepository<Medico, Long> {



  @Modifying
  @Transactional
  @Query("UPDATE Medico m SET m.ativo = 0 WHERE m.id = :id")
  void delete(Long id);

  Page<Medico> findAllByAtivo(Pageable pagination, int i);
}
