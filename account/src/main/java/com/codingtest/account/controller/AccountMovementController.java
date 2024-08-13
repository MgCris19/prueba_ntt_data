package com.codingtest.account.controller;

import com.codingtest.account.dto.ReportsDto;
import com.codingtest.account.dto.request.AccountMovementRequest;
import com.codingtest.account.dto.response.*;
import com.codingtest.account.exception.ResourceNotFoundException;
import com.codingtest.account.service.impl.AccountMovementServiceImpl;
import com.codingtest.account.validation.ValidationGroups;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.Error;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/movimientos")
public class AccountMovementController {

    @Autowired
    private AccountMovementServiceImpl accountMovementServiceImpl;

    @GetMapping
    public Response<AccountMovementsResponse, Error> getAllMovements() {
        var movements = accountMovementServiceImpl.findAll();

        var response = AccountMovementsResponse.builder().accountMovementsDto(movements).build();

        return Response.ok(response);
    }

    @GetMapping("/{id}")
    public Response<AccountMovementResponse, Error> getMovementById(@PathVariable Long id) throws ResourceNotFoundException {
        var movements = accountMovementServiceImpl.findById(id);

        var response = AccountMovementResponse.builder().accountMovementDto(movements).build();

        return Response.ok(response);
    }

    @GetMapping("/reportes")
    public Response<ReportsDto, Error> getReportes(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("clientName") String clientName) {
        var movements = accountMovementServiceImpl.getMovementsByDateAndClientName(clientName,startDate, endDate);

        return Response.ok(movements);
    }

    @PostMapping
    public Response<AccountMovementResponse, Error> createMovement( @Validated(ValidationGroups.CreateAcountMovement.class)
                                                                        @Valid @RequestBody AccountMovementRequest movementRequest) throws ResourceNotFoundException {
        var movement = accountMovementServiceImpl.save(movementRequest.getAccountMovementDto());

        var response = AccountMovementResponse.builder().accountMovementDto(movement).build();

        return Response.ok(response);
    }

    @PutMapping("/{id}")
    public Response<AccountMovementResponse, Error> updateMovement(@PathVariable Long id, @RequestBody AccountMovementRequest movementRequest) throws ResourceNotFoundException {
        var movement = accountMovementServiceImpl.updateAccountMovement(id,movementRequest.getAccountMovementDto());

        var response = AccountMovementResponse.builder().accountMovementDto(movement).build();

        return Response.ok(response);
    }

    @DeleteMapping("/{id}")
    public Response<AccountMovementResponse, Error> deleteMovement(@PathVariable Long id) throws ResourceNotFoundException {
        var movement = accountMovementServiceImpl.delete(id);

        var response = AccountMovementResponse.builder().accountMovementDto(movement).build();

        return Response.ok(response);
    }

}