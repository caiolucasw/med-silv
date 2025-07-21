package com.medsilveira.api.validations.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.medsilveira.api.dto.consultas.AgendamentoConsultaDTO;
import com.medsilveira.api.repositories.ConsultaRepository;

@Component
public class MedicoDisponivelValidation implements ConsultaValidationInterface {

  @Autowired
  ConsultaRepository consultaRepository;

  @Override
  public void validar(AgendamentoConsultaDTO dadosConsulta) {
    // Implementar a lógica de validação do médico disponível
    var medico = dadosConsulta.medicoId();

    if (medico == null) {
      return;
    }

    if (!consultaRepository.isMedicoDisponivel(medico, dadosConsulta.dataConsulta())) {
      throw new IllegalArgumentException("Médico não está disponível para a data da consulta");
    }

    // Exemplo: verificar se o médico está disponível para a data da consulta
    // Aqui você pode chamar um método no repositório para verificar a
    // disponibilidade
    // medicoRepository.isMedicoDisponivel(medico, dadosConsulta.dataConsulta());
  }

}
