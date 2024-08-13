package com.codingtest.account.mapper;

import com.codingtest.account.dto.AccountDto;
import com.codingtest.account.dto.AccountMovementDto;
import com.codingtest.account.dto.ClientDto;
import com.codingtest.account.dto.PersonDto;
import com.codingtest.account.entity.Account;
import com.codingtest.account.entity.AccountMovement;
import com.codingtest.account.entity.Client;
import com.codingtest.account.entity.Person;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountMovementMapper {

    public static AccountMovementDto toDto(AccountMovement accountMovement) {
        return AccountMovementDto.builder()
                .id(accountMovement.getId())
                .account(AccountDto.builder()
                        .id(accountMovement.getAccount().getId())
                        .client(ClientDto.builder()
                                .id(accountMovement.getAccount().getClient().getId())
                                .status(accountMovement.getAccount().getClient().getStatus())
                                .person(PersonDto.builder()
                                        .id(accountMovement.getAccount().getClient().getPerson().getId())
                                        .age(accountMovement.getAccount().getClient().getPerson().getAge())
                                        .address(accountMovement.getAccount().getClient().getPerson().getAddress())
                                        .name(accountMovement.getAccount().getClient().getPerson().getName())
                                        .phone(accountMovement.getAccount().getClient().getPerson().getPhone())
                                        .gender(accountMovement.getAccount().getClient().getPerson().getGender())
                                        .identification(accountMovement.getAccount().getClient().getPerson().getIdentification())
                                        .build())
                                .build())
                        .accountNumber(accountMovement.getAccount().getAccountNumber())
                        .status(accountMovement.getAccount().getStatus())
                        .accountType(accountMovement.getAccount().getAccountType())
                        .initialBalance(accountMovement.getAccount().getInitialBalance())
                        .build())
                .balance(accountMovement.getBalance())
                .created(accountMovement.getCreated())
                .transactionType(accountMovement.getTransactionType())
                .value(accountMovement.getValue())
                .build();
    }

    public static AccountMovement toEntity(AccountMovementDto accountMovementDto) {
        return AccountMovement.builder()
                .account(Account.builder()
                        .accountNumber(accountMovementDto.getAccount().getAccountNumber())
                        .status(accountMovementDto.getAccount().getStatus())
                        .accountType(accountMovementDto.getAccount().getAccountType())
                        .initialBalance(accountMovementDto.getAccount().getInitialBalance())
                        .build())
                .balance(accountMovementDto.getBalance())
                .created(accountMovementDto.getCreated())
                .transactionType(accountMovementDto.getTransactionType())
                .value(accountMovementDto.getValue())
                .build();
    }
}