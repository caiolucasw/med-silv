package com.medsilveira.api.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medsilveira.api.dto.consultas.AgendamentoConsultaDTO;
import com.medsilveira.api.dto.consultas.CancelamentoConsultaDTO;
import com.medsilveira.api.entities.Consulta;
import com.medsilveira.api.entities.Medico;
import com.medsilveira.api.entities.Paciente;
import com.medsilveira.api.enums.Especialidade;
import com.medsilveira.api.repositories.ConsultaRepository;
import com.medsilveira.api.repositories.MedicoRepository;
import com.medsilveira.api.repositories.PacienteRepository;

import jakarta.validation.Valid;

@Service
public class ConsultaService {

  @Autowired
  private ConsultaRepository consultaRepository;

  @Autowired
  private MedicoRepository medicoRepository;

  @Autowired
  private PacienteRepository pacienteRepository;

  public void agendar(AgendamentoConsultaDTO dadosConsulta) {

    Paciente paciente = pacienteRepository.findById(dadosConsulta.pacienteId())
        .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado"));

    if (dadosConsulta.medicoId() != null && !medicoRepository.existsById(dadosConsulta.medicoId()) == false) {
      throw new IllegalArgumentException("Médico não encontrado");
    }

    Medico medico = null;
    if (dadosConsulta.medicoId() == null) {
      // escolher médico aleatorio
      medico = this.escolherMedicoAleatorio(dadosConsulta.especialidade(), dadosConsulta.dataConsulta());
    } else {
      medico = medicoRepository.findById(dadosConsulta.medicoId()).get();
    }

    Consulta consulta = new Consulta(null, medico, paciente, dadosConsulta.dataConsulta());
    consultaRepository.save(consulta);
  }

  private Medico escolherMedicoAleatorio(Especialidade especialidade, LocalDateTime dataConsulta) {

    if (especialidade == null) {
      throw new IllegalArgumentException("Especialidade não pode ser nula");
    }
    // TODO Auto-generated method stub
    return medicoRepository.findRandomMedicoByEspecialidadeAndAvailable(especialidade, dataConsulta);
  }

  public void cancelar(CancelamentoConsultaDTO dados) {
    Consulta consulta = consultaRepository.getReferenceById(dados.consultaId());

    if (consulta == null) {
      throw new IllegalArgumentException("Consulta não encontrada");
    }

    // Verifica se a consulta já foi realizada
    if (consulta.getDataConsulta().isBefore(LocalDateTime.now())) {
      throw new IllegalArgumentException("Não é possível cancelar uma consulta já realizada");
    }

    // Verifica se a consulta está agendada para daqui a menos de 24 horas
    if (LocalDateTime.now().plusHours(24).isAfter(consulta.getDataConsulta())) {
      throw new IllegalArgumentException("Não é possível cancelar uma consulta com menos de 24 horas de antecedência");
    }

    consulta.cancelar(dados);
  }

}
