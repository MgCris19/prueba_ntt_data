package com.codingtest.account.controller;

import com.codingtest.account.dto.ReportsDto;
import com.codingtest.account.dto.request.AccountMovementRequest;
import com.codingtest.account.dto.response.*;
import com.codingtest.account.exception.ResourceNotFoundException;
import com.codingtest.account.service.AccountMovementService;
import com.codingtest.account.validation.ValidationGroups;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.Error;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/movimientos")
@RequiredArgsConstructor
public class AccountMovementController {

    private final AccountMovementService accountMovementService;

    @GetMapping
    public Response<AccountMovementsResponse, Error> getAllMovements() {
        var movements = accountMovementService.findAll();

        var response = AccountMovementsResponse.builder().accountMovementsDto(movements).build();

        return Response.ok(response);
    }

    @GetMapping("/{id}")
    public Response<AccountMovementResponse, Error> getMovementById(@PathVariable Long id) throws ResourceNotFoundException {
        var movements = accountMovementService.findById(id);

        var response = AccountMovementResponse.builder().accountMovementDto(movements).build();

        return Response.ok(response);
    }

    @GetMapping("/reportes")
    public Response<ReportsDto, Error> getReportes(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("clientName") String clientName) {
        var movements = accountMovementService.getMovementsByDateAndClientName(clientName,startDate, endDate);

        return Response.ok(movements);
    }

    @PostMapping
    public Response<AccountMovementResponse, Error> createMovement( @Validated(ValidationGroups.CreateAcountMovement.class)
                                                                        @Valid @RequestBody AccountMovementRequest movementRequest) throws ResourceNotFoundException {
        var movement = accountMovementService.save(movementRequest.getAccountMovementDto());

        var response = AccountMovementResponse.builder().accountMovementDto(movement).build();

        return Response.ok(response);
    }

    @PutMapping("/{id}")
    public Response<AccountMovementResponse, Error> updateMovement(@PathVariable Long id, @RequestBody AccountMovementRequest movementRequest) throws ResourceNotFoundException {
        var movement = accountMovementService.updateAccountMovement(id,movementRequest.getAccountMovementDto());

        var response = AccountMovementResponse.builder().accountMovementDto(movement).build();

        return Response.ok(response);
    }

    @DeleteMapping("/{id}")
    public Response<AccountMovementResponse, Error> deleteMovement(@PathVariable Long id) throws ResourceNotFoundException {
        var movement = accountMovementService.delete(id);

        var response = AccountMovementResponse.builder().accountMovementDto(movement).build();

        return Response.ok(response);
    }

}