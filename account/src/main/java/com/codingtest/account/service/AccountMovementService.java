package com.codingtest.account.service;

import com.codingtest.account.dto.AccountMovementDto;
import com.codingtest.account.dto.ReportsDto;
import com.codingtest.account.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface AccountMovementService {

    List<AccountMovementDto> findAll();

    AccountMovementDto findById(Long id) throws ResourceNotFoundException;

    AccountMovementDto updateAccountMovement(Long id, AccountMovementDto accountMovementDto) throws ResourceNotFoundException;

    AccountMovementDto save(AccountMovementDto accountMovementDto) throws ResourceNotFoundException;

    AccountMovementDto delete(Long id) throws ResourceNotFoundException;

    ReportsDto getMovementsByDateAndClientName(String clientName, LocalDate starDate, LocalDate endDate);
}
