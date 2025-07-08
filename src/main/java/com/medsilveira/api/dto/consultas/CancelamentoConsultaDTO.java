package com.medsilveira.api.dto.consultas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CancelamentoConsultaDTO(@NotNull Long consultaId, @NotBlank String motivo) {

}
