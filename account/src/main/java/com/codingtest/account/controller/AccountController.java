package com.codingtest.account.controller;

import com.codingtest.account.dto.AccountDto;
import com.codingtest.account.dto.request.AccountRequest;
import com.codingtest.account.dto.response.AccountResponse;
import com.codingtest.account.dto.response.AccountsResponse;
import com.codingtest.account.dto.response.Response;
import com.codingtest.account.exception.ResourceFoundException;
import com.codingtest.account.exception.ResourceNotFoundException;
import com.codingtest.account.service.impl.AccountServiceImpl;
import com.codingtest.account.validation.ValidationGroups;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cuentas")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @GetMapping
    public Response<AccountsResponse, Error> getAllAccounts() {
        var accounts = accountServiceImpl.findAll();

        var response = AccountsResponse.builder().accounts(accounts).build();

        return Response.ok(response);
    }

    @GetMapping("/{id}")
    public Response<AccountResponse, Error> getAccountById(@PathVariable Long id) throws ResourceNotFoundException {
        var account = accountServiceImpl.findById(id);

        var response = AccountResponse.builder().account(account).build();

        return Response.ok(response);
    }

    @GetMapping("/{accountNumber}")
    public Response<AccountResponse, Error> getAccountByAccountNumber(@PathVariable String accountNumber) throws ResourceNotFoundException {
        var account = accountServiceImpl.findAccountByAccountNumber(accountNumber);

        var response = AccountResponse.builder().account(account).build();

        return Response.ok(response);
    }

    @PostMapping
    public Response<AccountResponse, Error> createAccount(@Validated(ValidationGroups.CreateAccount.class)
                                                              @Valid @RequestBody AccountRequest accountRequest) throws ResourceFoundException, ResourceNotFoundException {
        var account = accountServiceImpl.save(accountRequest.getAccountDto());

        var response = AccountResponse.builder().account(account).build();

        return Response.ok(response);
    }

    @PutMapping("/")
    public Response<AccountResponse, Error> updateAccount(@Valid @RequestBody AccountRequest accountRequest) throws ResourceFoundException,ResourceNotFoundException {
        var account = accountServiceImpl.updateAccount(accountRequest.getAccountDto());

        var response = AccountResponse.builder().account(account).build();

        return Response.ok(response);
    }

    @DeleteMapping("/{id}")
    public Response<AccountResponse, Error> deleteAccount(@PathVariable Long id) throws ResourceNotFoundException {
        var account = accountServiceImpl.deleteAccount(null,id);

        var response = AccountResponse.builder().account(account).build();

        return Response.ok(response);
    }

    @DeleteMapping("/numeroCuenta/{accountNumber}")
    public Response<AccountResponse, Error> deleteByAccountNumber(@PathVariable String accountNumber) throws ResourceNotFoundException {
        var account = accountServiceImpl.deleteAccount(accountNumber,null);

        var response = AccountResponse.builder().account(account).build();

        return Response.ok(response);
    }

    @DeleteMapping("/desactivarCuentas/{name}")
    public List<AccountDto>  deleteAccountsByClientName(@PathVariable String name) {
        return accountServiceImpl.deleteAccountsByClient(name);
    }

}