package com.medsilveira.api.dto.pacientes;

import com.medsilveira.api.dto.DadosEnderecoDTO;

public record PacienteAtualizaDadosDTO(String nome, String telefone, DadosEnderecoDTO endereco) {
}
