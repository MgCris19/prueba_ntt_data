package com.codingtest.account.controller;

import com.codingtest.account.model.Account;
import com.codingtest.account.model.Client;
import com.codingtest.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cuentas")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return accountService.findById(id);
    }

    @GetMapping("/{accountNumber}")
    public Account getAccountByAccountNumber(@PathVariable String accountNumber) {
        return accountService.findAccountByAccountNumber(accountNumber);
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        return accountService.save(account);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateAccount(@RequestBody Account account) {
        return accountService.updateAccount(account);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>  deleteAccount(@PathVariable Long id) {
        return accountService.deleteAccount(null,id);
    }

    @DeleteMapping("/numeroCuenta/{accountNumber}")
    public ResponseEntity<?>  deleteByAccountNumber(@PathVariable String accountNumber) {
       return accountService.deleteAccount(accountNumber,null);
    }

    @DeleteMapping("/desactivarCuentas/{name}")
    public ResponseEntity<?>  deleteAccountsByClientName(@PathVariable String name) {
        return accountService.deleteAccountsByClient(name);
    }

}
