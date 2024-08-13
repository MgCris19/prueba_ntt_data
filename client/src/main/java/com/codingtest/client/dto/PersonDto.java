package com.codingtest.client.dto;

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
public class PersonDto {

    private Long id;

    @NotNull(message = "Nombre de persona no puede ser null")
    @NotBlank(message = "Nombre de persona no puede ser vacio")
    private String name;

    private String gender;

    private Integer age;

    private String identification;

    @NotNull
    private String address;

    @NotNull
    private String phone;
}
