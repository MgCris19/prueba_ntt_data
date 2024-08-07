package com.codingtest.account.controller;

import com.codingtest.account.dto.Reports;
import com.codingtest.account.model.AccountMovement;
import com.codingtest.account.service.AccountMovementService;
import com.codingtest.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movimientos")
public class AccountMovementController {

    @Autowired
    private AccountMovementService accountMovementService;

    @GetMapping
    public List<AccountMovement> getAllMovements() {
        return accountMovementService.findAll();
    }

    @GetMapping("/{id}")
    public AccountMovement getMovementById(@PathVariable Long id) {
        return accountMovementService.findById(id);
    }

    @GetMapping("/reportes")
    public ResponseEntity<?> getReportes(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam("clientName") String clientName) {

        // Llama al servicio para obtener los reportes dentro del rango de fechas
        return accountMovementService.getMovementsByDateAndClientName(clientName,startDate, endDate);
    }

    @PostMapping
    public ResponseEntity<?> createMovement(@RequestBody AccountMovement movement) {
        return accountMovementService.save(movement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovement(@PathVariable Long id, @RequestBody AccountMovement movement) {
        return accountMovementService.updateAccountMovement(id,movement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovement(@PathVariable Long id) {
        return accountMovementService.delete(id);
    }

}
