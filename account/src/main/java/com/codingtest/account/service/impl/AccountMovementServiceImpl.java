package com.codingtest.account.service.impl;

import com.codingtest.account.dto.AccountMovementDto;
import com.codingtest.account.dto.ReportsDto;
import com.codingtest.account.entity.Account;
import com.codingtest.account.entity.AccountMovement;
import com.codingtest.account.exception.ResourceNotFoundException;
import com.codingtest.account.mapper.AccountMovementMapper;
import com.codingtest.account.repository.AccountMovementRepository;
import com.codingtest.account.repository.AccountRepository;
import com.codingtest.account.service.AccountMovementService;
import com.codingtest.account.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountMovementServiceImpl implements AccountMovementService {

    private final AccountMovementRepository accountMovementRepository;

    private final AccountRepository accountRepository;

    @Override
    public List<AccountMovementDto> findAll() {
        return accountMovementRepository.findAll().stream().map(AccountMovementMapper::toDto).toList();
    }

    @Override
    public AccountMovementDto findById(Long id) throws ResourceNotFoundException {
        var accountMovementDto = accountMovementRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.ACCOUNT_MOVEMENT_NOT_FOUND));

        return AccountMovementMapper.toDto(accountMovementDto);
    }

    @Override
    public AccountMovementDto updateAccountMovement(Long id, AccountMovementDto accountMovementDto) throws ResourceNotFoundException {

        //no queda tan claro que se debe actualizar en movimiento de cuenta, en teoria esta entidad no debería ser actualizada
        //sino creando un nuevo registro que haga un ajuste por la trazabilidad del historico
        AccountMovement existingAccountMovement = accountMovementRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.ACCOUNT_MOVEMENT_NOT_FOUND));

        existingAccountMovement.setTransactionType(accountMovementDto.getTransactionType());
        existingAccountMovement.setValue(accountMovementDto.getValue().equals(BigDecimal.ZERO)
                ? existingAccountMovement.getValue()
                : accountMovementDto.getValue());

        return AccountMovementMapper.toDto(accountMovementRepository.save(existingAccountMovement));
    }

    @Override
    @Transactional
    public AccountMovementDto save(AccountMovementDto accountMovementDto) throws ResourceNotFoundException {
        Account existingAccount = accountRepository
                .findAccountByAccountNumber(accountMovementDto.getAccount().getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException(Messages.ACCOUNT_NOT_FOUND));

        existingAccount.setAvailableBalance(existingAccount.getAvailableBalance().add(accountMovementDto.getValue()));

        if (existingAccount.getAvailableBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException(Messages.BALANCE_NOT_AVAILABLE);
        }
        existingAccount = accountRepository.save(existingAccount);

        accountMovementDto.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        accountMovementDto.setBalance(existingAccount.getAvailableBalance());

        var accountMovement = AccountMovementMapper.toEntity(accountMovementDto);
        accountMovement.setAccount(existingAccount);

        return AccountMovementMapper.toDto(accountMovementRepository.save(accountMovement));
    }

    @Override
    public AccountMovementDto delete(Long id) throws ResourceNotFoundException {

        AccountMovement existingAccountMovement = accountMovementRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Messages.ACCOUNT_MOVEMENT_NOT_FOUND));

        accountMovementRepository.delete(existingAccountMovement);
        return AccountMovementMapper.toDto(existingAccountMovement);

    }

    @Override
    public ReportsDto getMovementsByDateAndClientName(String clientName
            , LocalDate starDate, LocalDate endDate) {

        Timestamp startTimestamp = Timestamp.valueOf(starDate.atStartOfDay());
        Timestamp endTimestamp = Timestamp.valueOf(endDate.atStartOfDay());

        List<AccountMovement> movements = accountMovementRepository.findMovementsByDateAndClientName(clientName, startTimestamp, endTimestamp).orElse(new ArrayList<>());
        ReportsDto reportsDto = new ReportsDto();
        reportsDto.setMovements(new ArrayList<>());

        movements.forEach(e -> {
            ReportsDto.Movement movement = new ReportsDto.Movement();
            movement.setCliente(e.getAccount().getClient().getPerson().getName());
            movement.setFecha(e.getCreated().toLocalDateTime()
                    .atZone(ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            movement.setMovimiento(e.getValue());
            movement.setNumeroCuenta(e.getAccount().getAccountNumber());
            movement.setSaldoDisponible(e.getBalance());
            movement.setTipoCuenta(e.getAccount().getAccountType());
            movement.setEstado(e.getAccount().getStatus());
            reportsDto.getMovements().add(movement);
        });
        return reportsDto;
    }
}
