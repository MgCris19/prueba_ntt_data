package com.codingtest.account.dto;

import com.codingtest.account.validation.ValidationGroups;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountDto {

    private Long id;

    @NotNull(message = "Cliente no puede ser null", groups = ValidationGroups.CreateAccount.class)
    @Valid
    private ClientDto client;

    @NotNull(message = "Numero de cuenta no puede ser null")
    @NotBlank(message = "Numero de cuenta no puede estar vacio")
    @Valid
    private String accountNumber;

    private String accountType;

    private BigDecimal initialBalance;

    private BigDecimal availableBalance;

    private Boolean status;
}