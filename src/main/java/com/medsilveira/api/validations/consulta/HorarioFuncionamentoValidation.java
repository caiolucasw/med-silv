package com.medsilveira.api.validations.consulta;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.medsilveira.api.dto.consultas.AgendamentoConsultaDTO;

@Component
public class HorarioFuncionamentoValidation implements ConsultaValidationInterface {

  @Override
  public void validar(AgendamentoConsultaDTO dadosConsulta) {
    var dataConsulta = dadosConsulta.dataConsulta();
    // Implementar a lógica de validação do horário de funcionam

    var isDomingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    var isForaHorario = dataConsulta.getHour() < 7 || dataConsulta.getHour() > 18;

    // Exemplo: verificar se a consulta está agendada dentro do horário permitido

    if (isDomingo || isForaHorario) {
      throw new IllegalArgumentException(
          "Consulta não pode ser agendada aos domingos ou fora do horário de funcionamento (07:00 - 18:00)");
    }
  }

}
