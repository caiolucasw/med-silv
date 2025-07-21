package com.medsilveira.api.validations.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.medsilveira.api.dto.consultas.AgendamentoConsultaDTO;
import com.medsilveira.api.repositories.MedicoRepository;

@Component
public class MedicoAtivoValidation implements ConsultaValidationInterface {

  @Autowired
  private MedicoRepository medicoRepository;

  @Override
  public void validar(AgendamentoConsultaDTO dadosConsulta) {
    // Implementar a lógica de validação do médico ativo
    var medico = dadosConsulta.medicoId();

    if (medico == null) {
      return;
    }

    // Exemplo: verificar se o médico está ativo
    if (!medicoRepository.existsByIdAndAtivo(medico)) {
      throw new IllegalArgumentException("Médico não está ativo ou não foi encontrado");
    }
  }

}
