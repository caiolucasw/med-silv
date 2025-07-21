package com.medsilveira.api.validations.consulta;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.medsilveira.api.dto.consultas.AgendamentoConsultaDTO;

@Component
public class HorarioAntecedenciaValidation implements ConsultaValidationInterface {

  @Override
  public void validar(AgendamentoConsultaDTO dadosConsulta) {
    var dataConsulta = dadosConsulta.dataConsulta();
    // Implementar a lógica de validação do horário de antecedência
    var agora = LocalDateTime.now();
    var diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();

    // Exemplo: verificar se a consulta está agendada com pelo menos 24 horas de
    // antecedência
    if (diferencaEmMinutos < 30) {
      throw new IllegalArgumentException("Consulta deve ser agendada com pelo menos 30 minutos de antecedência");
    }
  }
}