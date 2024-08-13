package com.codingtest.account.service.impl;

import com.codingtest.account.dto.AccountDto;
import com.codingtest.account.dto.response.Response;
import com.codingtest.account.entity.Account;
import com.codingtest.account.entity.Client;
import com.codingtest.account.exception.ResourceFoundException;
import com.codingtest.account.exception.ResourceNotFoundException;
import com.codingtest.account.mapper.AccountMapper;
import com.codingtest.account.mapper.ClientMapper;
import com.codingtest.account.repository.AccountRepository;
import com.codingtest.account.repository.ClientRepository;
import com.codingtest.account.service.AccountService;
import com.codingtest.account.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final ClientRepository clientRepository;

    @Override
    public List<AccountDto> findAll() {
        return accountRepository.findAll().stream().map(AccountMapper::toDto).toList();
    }

    @Override
    public AccountDto findById(Long id)  throws ResourceNotFoundException {
        var account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Messages.CLIENT_NOT_FOUND));

        return AccountMapper.toDto(account);
    }

    @Override
    public AccountDto findAccountByAccountNumber(String accountNumber)  throws ResourceNotFoundException {
        var account = accountRepository.findAccountByAccountNumber(accountNumber).orElseThrow(() -> new ResourceNotFoundException(Messages.CLIENT_NOT_FOUND));

        return AccountMapper.toDto(account);
    }

    @Override
    public AccountDto save(AccountDto accountDto) throws ResourceFoundException,ResourceNotFoundException {

        //se usa nombre porque me estoy basando en los casos de uso, esto no es lo ideal
        Client client = clientRepository.findByName(accountDto.getClient().getPerson().getName()).orElse(null);

        if(client == null){
            throw new ResourceNotFoundException(Messages.CLIENT_NOT_FOUND);
        }
        Account existingAccount = accountRepository.findAccountByAccountNumber(accountDto.getAccountNumber()).orElse(null);

        if(existingAccount!=null){
            throw new ResourceFoundException(String.format(Messages.ACCOUNT_EXIST, accountDto.getAccountNumber()));
        }

        var account = AccountMapper.toEntity(accountDto);

        account.setClient(client);
        account.setAvailableBalance(account.getInitialBalance());

        return AccountMapper.toDto(accountRepository.save(account));

    }

    @Override
    public AccountDto updateAccount(AccountDto accountDto) throws ResourceNotFoundException{

        Account existingAccount = accountRepository
                .findAccountByAccountNumber(accountDto.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException(Messages.ACCOUNT_NOT_FOUND));

        existingAccount.setAccountNumber(accountDto.getAccountNumber());
        existingAccount.setAccountType(accountDto.getAccountType());
        existingAccount.setStatus(accountDto.getStatus());

        return AccountMapper.toDto(accountRepository.save(existingAccount));
    }

    @Override
    public AccountDto delete(Long id) throws ResourceNotFoundException{
        Account existingAccount = accountRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.ACCOUNT_NOT_FOUND));

        existingAccount.setStatus(Boolean.FALSE);
        accountRepository.save(existingAccount);

        return AccountMapper.toDto(existingAccount);
    }

    @Override
    public AccountDto deleteAccount(String accountNumber, Long id) throws ResourceNotFoundException{

        Account existingAccount = accountNumber!=null
                ?accountRepository.findAccountByAccountNumber(accountNumber)
                                  .orElseThrow(() -> new ResourceNotFoundException(Messages.CLIENT_NOT_FOUND))
                :accountRepository.findById(id)
                                  .orElseThrow(() -> new ResourceNotFoundException(Messages.CLIENT_NOT_FOUND));

            existingAccount.setStatus(Boolean.FALSE);

            return AccountMapper.toDto(accountRepository.save(existingAccount));

    }

    @Override
    public List<AccountDto> deleteAccountsByClient(String name){

           List<Account> accountList = accountRepository.findAccountsByClientName(name).orElse(new ArrayList<>());
           List<AccountDto> accountDtoList = new ArrayList<>();
           accountList.forEach(e->{
                e.setStatus(Boolean.FALSE);
               accountDtoList.add(AccountMapper.toDto(accountRepository.save(e)));
            });

            return accountDtoList;
    }

}