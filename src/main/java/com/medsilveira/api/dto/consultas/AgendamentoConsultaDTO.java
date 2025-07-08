package com.medsilveira.api.dto.consultas;

import java.time.LocalDateTime;

import com.medsilveira.api.enums.Especialidade;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record AgendamentoConsultaDTO(

        Long medicoId,

        @NotNull Long pacienteId,

        @NotNull @Future LocalDateTime dataConsulta,

        Especialidade especialidade

) {
}
