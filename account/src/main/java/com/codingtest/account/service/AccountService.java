package com.codingtest.account.service;

import com.codingtest.account.config.Logging;
import com.codingtest.account.dto.ApiResponse;
import com.codingtest.account.model.Account;
import com.codingtest.account.model.Client;
import com.codingtest.account.repository.AccountRepository;
import com.codingtest.account.repository.ClientRepository;
import com.codingtest.account.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public Account findAccountByAccountNumber(String accountNumber) {
        return accountRepository.findAccountByAccountNumber(accountNumber).orElse(null);
    }

    public ResponseEntity<?> save(Account account) {
        ApiResponse response = new ApiResponse();
        try {
            //se usa nombre porque me estoy basando en los casos de uso, esto no es lo ideal
            Client client = clientRepository.findByName(account.getClient().getPerson().getName()).orElse(null);
            Account existingAccount = findAccountByAccountNumber(account.getAccountNumber());
            if(client == null){
                response.setMessage(Messages.CLIENT_NOT_FOUND);
                response.setError(new ApiResponse.Error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), Messages.CLIENT_NOT_FOUND));
                response.setSuccess(Boolean.FALSE);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if(existingAccount!=null){
                response.setMessage(String.format(Messages.ACCOUNT_EXIST, account.getAccountNumber()));
                response.setError(new ApiResponse.Error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), response.getMessage()));
                response.setSuccess(Boolean.FALSE);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            account.setClient(client);
            account.setAvailableBalance(account.getInitialBalance());
            return ResponseEntity.ok(accountRepository.save(account));
        }catch (Exception e){
            response.setMessage(e.getMessage());
            response.setError(new ApiResponse.Error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage()));
            response.setSuccess(Boolean.FALSE);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateAccount(Account account) {
        ApiResponse response = new ApiResponse();

        try {
            Account existingAccount = findAccountByAccountNumber(account.getAccountNumber());

            if (existingAccount != null) {
                existingAccount.setAccountNumber(account.getAccountNumber());
                existingAccount.setAccountType(account.getAccountType());
                existingAccount.setStatus(account.getStatus());
                response.setSuccess(Boolean.TRUE);
                response.setData(accountRepository.save(existingAccount));
                return ResponseEntity.ok(response);
            } else {
                response.setMessage(Messages.ACCOUNT_NOT_FOUND);
                response.setError(new ApiResponse.Error(String.valueOf(HttpStatus.NOT_FOUND.value()), Messages.ACCOUNT_NOT_FOUND));
                response.setSuccess(Boolean.FALSE);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            Logging.error(e.getMessage());
            response.setMessage(e.getMessage());
            response.setError(new ApiResponse.Error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage()));
            response.setSuccess(Boolean.FALSE);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void delete(Long id) {
        accountRepository.deleteById(id);
    }

    public ResponseEntity<?> deleteAccount(String accountNumber, Long id) {
        ApiResponse response = new ApiResponse();

        try {
            Account existingAccount = accountNumber!=null
                    ?findAccountByAccountNumber(accountNumber)
                    :findById(id);
            if (existingAccount != null) {
                existingAccount.setStatus(Boolean.FALSE);
                response.setSuccess(Boolean.TRUE);
                response.setData(accountRepository.save(existingAccount));
                return ResponseEntity.ok(response);
            } else {
                response.setMessage(Messages.ACCOUNT_NOT_FOUND);
                response.setError(new ApiResponse.Error(String.valueOf(HttpStatus.NOT_FOUND.value()), Messages.ACCOUNT_NOT_FOUND));
                response.setSuccess(Boolean.FALSE);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            Logging.error(e.getMessage());
            response.setMessage(e.getMessage());
            response.setError(new ApiResponse.Error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage()));
            response.setSuccess(Boolean.FALSE);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteAccountsByClient(String name) {
        ApiResponse response = new ApiResponse();

        try {
           List<Account> accountList = accountRepository.findAccountsByClientName(name).orElse(new ArrayList<>());
            accountList.forEach(e->{
                e.setStatus(Boolean.FALSE);
                accountRepository.save(e);
            });
            response.setSuccess(Boolean.TRUE);
            response.setMessage(Messages.USER_ACCOUNTS_DELETION_SUCCESS);;
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Logging.error(e.getMessage());
            response.setMessage(e.getMessage());
            response.setError(new ApiResponse.Error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage()));
            response.setSuccess(Boolean.FALSE);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}