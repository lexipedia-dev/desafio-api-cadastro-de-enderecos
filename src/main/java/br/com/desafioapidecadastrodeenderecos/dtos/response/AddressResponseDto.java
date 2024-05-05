package br.com.desafioapidecadastrodeenderecos.dtos.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressResponseDto(
        @NotBlank String street,
        @NotBlank String cep,
        @NotBlank String streetNumber,
        @NotBlank String city,
        @NotBlank String state,
        @NotNull Boolean mainAddress
) {
}

