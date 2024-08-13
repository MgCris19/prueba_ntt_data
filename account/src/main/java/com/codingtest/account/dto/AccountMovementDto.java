package com.codingtest.account.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountMovementDto {

    private Long id;

    @NotNull(message = "Cuenta no puede ser null")
    @Valid
    private AccountDto account;

    private Timestamp created;

    private String transactionType;

    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor que cero")
    @Valid
    private BigDecimal value;

    private BigDecimal balance;

}