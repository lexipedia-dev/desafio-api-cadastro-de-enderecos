package br.com.desafioapidecadastrodeenderecos.dtos.response;

import br.com.desafioapidecadastrodeenderecos.config.serialization.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponseDto(
        @NotBlank String name,
        @NotNull @Past @JsonSerialize(using = LocalDateTimeSerializer.class) LocalDateTime birthday,
        @Valid List<AddressResponseDto> addresses
) {
}
