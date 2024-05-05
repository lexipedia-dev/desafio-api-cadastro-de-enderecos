package br.com.desafioapidecadastrodeenderecos.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressCreateRequestDto(
        @NotBlank String street,
        @NotBlank String cep,
        @NotBlank String streetNumber,
        @NotBlank String city,
        @NotBlank String state,
        @NotNull Boolean mainAddress
) {
}

