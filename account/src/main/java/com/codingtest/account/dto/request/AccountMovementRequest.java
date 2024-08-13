package com.codingtest.account.dto.request;

import com.codingtest.account.dto.AccountMovementDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountMovementRequest {

    @NotNull
    @Valid
    AccountMovementDto accountMovementDto;
}
