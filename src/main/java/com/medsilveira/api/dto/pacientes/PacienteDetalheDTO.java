package com.medsilveira.api.dto.pacientes;

import com.medsilveira.api.entities.Endereco;
import com.medsilveira.api.entities.Paciente;

public record PacienteDetalheDTO(Long id, String nome, String email, String crm, String telefone,
    Endereco endereco) {

  public PacienteDetalheDTO(Paciente paciente) {
    this(
        paciente.getId(),
        paciente.getNome(),
        paciente.getEmail(),
        paciente.getCpf(),
        paciente.getTelefone(),
        paciente.getEndereco());
  }

}
