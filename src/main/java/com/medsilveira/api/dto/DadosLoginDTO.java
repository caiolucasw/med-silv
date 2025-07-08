package com.medsilveira.api.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosLoginDTO(@NotBlank String login,
    @NotBlank String senha) {

}
