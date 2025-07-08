package com.medsilveira.api.dto.consultas;

import java.time.LocalDateTime;

public record ConsultaDetalheDTO(Long id, Long medicoId, Long pacienteId, LocalDateTime data) {

}
