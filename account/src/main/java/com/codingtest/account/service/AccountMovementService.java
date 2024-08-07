package com.codingtest.account.service;

import com.codingtest.account.config.Logging;
import com.codingtest.account.dto.ApiResponse;
import com.codingtest.account.dto.Reports;
import com.codingtest.account.model.Account;
import com.codingtest.account.model.AccountMovement;
import com.codingtest.account.repository.AccountMovementRepository;
import com.codingtest.account.repository.AccountRepository;
import com.codingtest.account.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountMovementService {

    @Autowired
    private AccountMovementRepository accountMovementRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<AccountMovement> findAll() {
        return accountMovementRepository.findAll();
    }

    public AccountMovement findById(Long id) {
        return accountMovementRepository.findById(id).orElse(null);
    }

    public ResponseEntity<?> updateAccountMovement(Long id,AccountMovement accountMovement) {
        ApiResponse response = new ApiResponse();

        try {
            //no queda tan claro que se debe actualizar en movimiento de cuenta, en teoria esta entidad no debería ser actualizada
            //sino creando un nuevo registro que haga un ajuste por la trazabilidad del historico
            AccountMovement existingAccountMovement = findById(id);

            if (existingAccountMovement != null) {
                existingAccountMovement.setTransactionType(accountMovement.getTransactionType());
                existingAccountMovement.setValue(accountMovement.getValue().equals(BigDecimal.ZERO)
                        ?existingAccountMovement.getValue()
                        :accountMovement.getValue());
                response.setSuccess(Boolean.TRUE);
                response.setData(accountMovementRepository.save(existingAccountMovement));
                return ResponseEntity.ok(response);
            } else {
                response.setMessage(Messages.ACCOUNT_MOVEMENT_NOT_FOUND);
                response.setError(new ApiResponse.Error(String.valueOf(HttpStatus.NOT_FOUND.value()), Messages.ACCOUNT_MOVEMENT_NOT_FOUND));
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

    @Transactional
    public ResponseEntity<?> save(AccountMovement accountMovement) {
        ApiResponse response = new ApiResponse();

        try {
            Account account = accountRepository.findAccountByAccountNumber(accountMovement.getAccount().getAccountNumber()).orElse(null);

            if(account==null){
                response.setMessage(Messages.ACCOUNT_NOT_FOUND);
                response.setError(new ApiResponse.Error(String.valueOf(HttpStatus.NOT_FOUND.value()), Messages.ACCOUNT_NOT_FOUND));
                response.setSuccess(Boolean.FALSE);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            account.setAvailableBalance(account.getAvailableBalance().add(accountMovement.getValue()));

            accountMovement.setAccount(account);
            accountMovement.setCreated(Timestamp.valueOf(LocalDateTime.now()));
            accountMovement.setBalance(account.getAvailableBalance());

            if(account.getAvailableBalance().compareTo(BigDecimal.ZERO)<0){
                throw new RuntimeException(Messages.BALANCE_NOT_AVAILABLE);
            }

            return ResponseEntity.ok(accountMovementRepository.save(accountMovement));
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            response.setMessage(e.getMessage());
            response.setError(new ApiResponse.Error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage()));
            response.setSuccess(Boolean.FALSE);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> delete(Long id) {
        ApiResponse response = new ApiResponse();

        try {
            AccountMovement existingMovement = findById(id);
            if (existingMovement != null) {
                accountMovementRepository.delete(existingMovement);
                response.setSuccess(Boolean.TRUE);
                response.setMessage(Messages.ACCOUNT_MOVEMENT_DELETE_SUCCESS);
                return ResponseEntity.ok(response);
            } else {
                response.setMessage(Messages.ACCOUNT_MOVEMENT_NOT_FOUND);
                response.setError(new ApiResponse.Error(String.valueOf(HttpStatus.NOT_FOUND.value()), Messages.ACCOUNT_MOVEMENT_NOT_FOUND));
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

    public ResponseEntity<?> getMovementsByDateAndClientName(String clientName, LocalDate starDate,LocalDate endDate) {
        ApiResponse response = new ApiResponse();

        try {
            Timestamp startTimestamp = Timestamp.valueOf(starDate.atStartOfDay());
            Timestamp endTimestamp = Timestamp.valueOf(endDate.atStartOfDay());

            List<AccountMovement> movements =  accountMovementRepository.findMovementsByDateAndClientName(clientName,startTimestamp,endTimestamp).orElse(new ArrayList<>());
            Reports reports = new Reports();
            reports.setMovements(new ArrayList<>());

            movements.forEach(e->{
                Reports.Movement movement = new Reports.Movement();
                movement.setCliente(e.getAccount().getClient().getPerson().getName());
                movement.setFecha(e.getCreated().toLocalDateTime()
                        .atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                movement.setMovimiento(e.getValue());
                movement.setNumeroCuenta(e.getAccount().getAccountNumber());
                movement.setSaldoDisponible(e.getBalance());
                movement.setTipoCuenta(e.getAccount().getAccountType());
                movement.setEstado(e.getAccount().getStatus());
                reports.getMovements().add(movement);
            });
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            Logging.error(e.getMessage());
            response.setMessage(e.getMessage());
            response.setError(new ApiResponse.Error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage()));
            response.setSuccess(Boolean.FALSE);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}