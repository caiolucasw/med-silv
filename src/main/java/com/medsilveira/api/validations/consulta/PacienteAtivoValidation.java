package com.medsilveira.api.validations.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.medsilveira.api.dto.consultas.AgendamentoConsultaDTO;

import com.medsilveira.api.repositories.PacienteRepository;

@Component
public class PacienteAtivoValidation implements ConsultaValidationInterface {

  @Autowired
  private PacienteRepository pacienteRepository;

  @Override
  public void validar(AgendamentoConsultaDTO dadosConsulta) {
    // Implementar a lógica de validação do médico ativo
    var paciente = dadosConsulta.pacienteId();

    if (paciente == null) {
      throw new IllegalArgumentException("Paciente não informado");
    }

    // Exemplo: verificar se o médico está ativo
    if (!pacienteRepository.existsByIdAndAtivo(paciente)) {
      throw new IllegalArgumentException("Paciente não está ativo ou não foi encontrado");
    }
  }

}
