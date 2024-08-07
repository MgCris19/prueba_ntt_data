package com.codingtest.account.repository;

import com.codingtest.account.model.Account;
import com.codingtest.account.model.AccountMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT p FROM Account p WHERE p.accountNumber = :accountNumber")
    public Optional<Account> findAccountByAccountNumber(@Param("accountNumber") String accountNumber);

    @Query("SELECT m FROM Account m WHERE m.client.person.name = :name ")
    public Optional<List<Account>> findAccountsByClientName(@Param("name") String name);
}