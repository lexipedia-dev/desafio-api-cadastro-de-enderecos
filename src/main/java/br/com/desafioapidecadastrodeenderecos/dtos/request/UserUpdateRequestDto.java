package br.com.desafioapidecadastrodeenderecos.dtos.request;

import br.com.desafioapidecadastrodeenderecos.config.serialization.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDateTime;

public record UserUpdateRequestDto(
        @NotBlank String name,
        @NotNull @Past @JsonSerialize(using = LocalDateTimeSerializer.class) LocalDateTime birthday
) {
}
