package com.medsilveira.api.repositories;

import com.medsilveira.api.entities.Medico;
import com.medsilveira.api.enums.Especialidade;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

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

  @Query("""
      select m from Medico m
      where
      m.ativo = 1
      and
      m.especialidade = :especialidade
      and
      m.id not in (
              select c.medico.id from Consulta c
              where
              c.dataConsulta = :data
      )
      order by rand()
      limit 1
      """)
  Medico findRandomMedicoByEspecialidadeAndAvailable(Especialidade especialidade, LocalDateTime data);

  @Query("select count(m) > 0 from Medico m where m.id = :medicoId and m.ativo = 1")
  boolean existsByIdAndAtivo(Long medicoId);
}
