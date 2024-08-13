package com.codingtest.account.dto.response;

import com.codingtest.account.dto.AccountDto;
import com.codingtest.account.dto.ClientDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountsResponse {
    List<AccountDto> accounts;
}
