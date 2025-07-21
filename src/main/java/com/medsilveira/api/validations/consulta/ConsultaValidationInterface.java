package com.medsilveira.api.validations.consulta;

import com.medsilveira.api.dto.consultas.AgendamentoConsultaDTO;

public interface ConsultaValidationInterface {

  public void validar(AgendamentoConsultaDTO dadosConsulta);

}
