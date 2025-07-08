package com.medsilveira.api.dto.pacientes;

import com.medsilveira.api.entities.Paciente;

public record PacienteListItemDTO(Long id, String nome, String email, String cpf) {

  public PacienteListItemDTO(Paciente paciente) {
    this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf());
  }

}
