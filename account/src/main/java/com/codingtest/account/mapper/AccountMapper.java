package com.codingtest.account.mapper;

import com.codingtest.account.dto.AccountDto;
import com.codingtest.account.dto.ClientDto;
import com.codingtest.account.dto.PersonDto;
import com.codingtest.account.entity.Account;
import com.codingtest.account.entity.Client;
import com.codingtest.account.entity.Person;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountMapper {

    public static AccountDto toDto(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .client(ClientDto.builder()
                        .id(account.getClient().getId())
                        .status(account.getClient().getStatus())
                        .person(PersonDto.builder()
                                .id(account.getClient().getPerson().getId())
                                .age(account.getClient().getPerson().getAge())
                                .address(account.getClient().getPerson().getAddress())
                                .name(account.getClient().getPerson().getName())
                                .phone(account.getClient().getPerson().getPhone())
                                .gender(account.getClient().getPerson().getGender())
                                .identification(account.getClient().getPerson().getIdentification())
                                .build())
                        .build())
                .accountNumber(account.getAccountNumber())
                .status(account.getStatus())
                .accountType(account.getAccountType())
                .initialBalance(account.getInitialBalance())
                .build();
    }

    public static Account toEntity(AccountDto accountDto) {
        return Account.builder()
                .client(Client.builder()
                        .status(accountDto.getClient().getStatus())
                        .person(Person.builder()
                                .age(accountDto.getClient().getPerson().getAge())
                                .address(accountDto.getClient().getPerson().getAddress())
                                .name(accountDto.getClient().getPerson().getName())
                                .phone(accountDto.getClient().getPerson().getPhone())
                                .gender(accountDto.getClient().getPerson().getGender())
                                .identification(accountDto.getClient().getPerson().getIdentification())
                                .build())
                        .build())
                .accountNumber(accountDto.getAccountNumber())
                .status(accountDto.getStatus())
                .accountType(accountDto.getAccountType())
                .initialBalance(accountDto.getInitialBalance())
                .build();
    }
}
