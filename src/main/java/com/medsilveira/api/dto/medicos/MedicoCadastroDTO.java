package com.medsilveira.api.dto.medicos;

import com.medsilveira.api.dto.DadosEnderecoDTO;
import com.medsilveira.api.enums.Especialidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record MedicoCadastroDTO(
                @NotBlank String nome,

                @NotBlank @Email String email,

                @NotBlank String telefone,

                @NotBlank @Pattern(regexp = "\\d{4,6}") String crm,

                @NotNull Especialidade especialidade,

                @NotNull @Valid DadosEnderecoDTO endereco) {
}
