package com.medsilveira.api.dto;

import com.medsilveira.api.entities.Medico;
import com.medsilveira.api.enums.Especialidade;

public record MedicoListItemDTO(Long id, String nome, String email, String crm, Especialidade especialidade) {

    public MedicoListItemDTO(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(),medico.getCrm(), medico.getEspecialidade());
    }
}
