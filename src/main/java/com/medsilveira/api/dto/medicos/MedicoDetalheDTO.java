package com.medsilveira.api.dto.medicos;

import com.medsilveira.api.entities.Endereco;
import com.medsilveira.api.entities.Medico;
import com.medsilveira.api.enums.Especialidade;

public record MedicoDetalheDTO(Long id, String nome, String email, String crm, String telefone,
    Especialidade especialidade,
    Endereco endereco) {

  public MedicoDetalheDTO(Medico medico) {
    this(
        medico.getId(),
        medico.getNome(),
        medico.getEmail(),
        medico.getCrm(),
        medico.getTelefone(),
        medico.getEspecialidade(),
        medico.getEndereco());
  }

}
