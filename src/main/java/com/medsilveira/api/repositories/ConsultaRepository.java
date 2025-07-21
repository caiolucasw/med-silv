package com.medsilveira.api.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.medsilveira.api.entities.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Query("""
            select count(c) <= 0 from Consulta c
            where
            c.medico.id = :medicoId
            and
            c.dataConsulta = :dataConsulta
            """)
    boolean isMedicoDisponivel(Long medico, LocalDateTime dataConsulta);
    // Aqui você pode adicionar métodos personalizados de consulta, se necessário

    @Query("""
            select count(c) > 0 from Consulta c
            where
            c.paciente.id = :pacienteId
            and
            (c.dataConsulta BETWEEN :dataAbertura and :dataFechamento)
            """)
    boolean pacienteJaTemConsultaNoDia(Long pacienteId, LocalDateTime dataAbertura,
            LocalDateTime dataFechamento);

}
