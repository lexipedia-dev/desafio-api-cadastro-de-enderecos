package br.com.desafioapidecadastrodeenderecos.dtos.request;

import br.com.desafioapidecadastrodeenderecos.config.serialization.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDateTime;
import java.util.List;

public record UserCreateRequestDto(
        @NotBlank String name,
        @NotNull @Past @JsonSerialize(using = LocalDateTimeSerializer.class) LocalDateTime birthday,
        @Valid List<AddressCreateRequestDto> addresses
) {
}
