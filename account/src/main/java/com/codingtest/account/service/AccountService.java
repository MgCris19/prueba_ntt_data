package com.codingtest.account.service;

import com.codingtest.account.dto.AccountDto;
import com.codingtest.account.dto.response.Response;
import com.codingtest.account.entity.Account;
import com.codingtest.account.exception.ResourceFoundException;
import com.codingtest.account.exception.ResourceNotFoundException;

import java.util.List;

public interface AccountService {

    List<AccountDto> findAll();
    AccountDto findById(Long id)  throws ResourceNotFoundException;
    AccountDto findAccountByAccountNumber(String accountNumber)  throws ResourceNotFoundException;
    AccountDto save(AccountDto account)  throws ResourceFoundException,ResourceNotFoundException;
    AccountDto updateAccount(AccountDto account)  throws ResourceNotFoundException;
    AccountDto delete(Long id)  throws ResourceNotFoundException;
    AccountDto deleteAccount(String accountNumber, Long id)  throws ResourceNotFoundException;
    List<AccountDto> deleteAccountsByClient(String name) ;
}
