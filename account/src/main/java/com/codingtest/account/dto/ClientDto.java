package com.codingtest.account.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClientDto {

    private Long id;

    @NotNull(message = "Persona no puede ser null")
    @Valid
    private PersonDto person;

    private String password;

    private Boolean status;
}
