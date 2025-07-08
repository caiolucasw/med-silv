package com.medsilveira.api.dto.medicos;

import com.medsilveira.api.dto.DadosEnderecoDTO;

public record MedicoAtualizaDadosDTO(String nome, String telefone, DadosEnderecoDTO endereco) {
}
