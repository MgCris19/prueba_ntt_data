package com.codingtest.client.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClientDto {

    private Long id;

    @NotNull(message = "Persona no puede ser null")
    @Valid
    private PersonDto person;

    @NotNull(message = "Constraseña de cliente no puede ser null")
    @NotBlank(message = "Constraseña de cliente no puede ser vacio")
    private String password;

    private Boolean status;
}
