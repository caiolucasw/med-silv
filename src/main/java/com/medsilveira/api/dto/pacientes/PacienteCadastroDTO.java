package com.medsilveira.api.dto.pacientes;

import com.medsilveira.api.dto.DadosEnderecoDTO;
import com.medsilveira.api.enums.Especialidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PacienteCadastroDTO(
    @NotBlank String nome,

    @NotBlank @Email String email,

    @NotBlank String telefone,

    @NotBlank @Pattern(regexp = "\\d{11}") String cpf,

    @NotNull @Valid DadosEnderecoDTO endereco) {

}
