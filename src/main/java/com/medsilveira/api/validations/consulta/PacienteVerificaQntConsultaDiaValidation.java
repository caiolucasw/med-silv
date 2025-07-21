package com.medsilveira.api.validations.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.medsilveira.api.dto.consultas.AgendamentoConsultaDTO;
import com.medsilveira.api.repositories.ConsultaRepository;

@Component
public class PacienteVerificaQntConsultaDiaValidation implements ConsultaValidationInterface {

    @Autowired
    ConsultaRepository consultaRepository;

    @Override
    public void validar(AgendamentoConsultaDTO dadosConsulta) {
        // Implementar a lógica de validação da quantidade de consultas do paciente
        var pacienteId = dadosConsulta.pacienteId();

        if (pacienteId == null) {
            return;
        }

        var dataConsulta = dadosConsulta.dataConsulta();
        if (dataConsulta == null) {
            throw new IllegalArgumentException("Data da consulta não informada");
        }
        var dataAbertura = dataConsulta.withHour(7);
        var dataFechamento = dataConsulta.withHour(8);

        if (consultaRepository.pacienteJaTemConsultaNoDia(pacienteId, dataAbertura, dataFechamento)) {
            throw new IllegalArgumentException("Paciente já possui consulta agendada para o dia");
        }

    }

}
