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
public interface AccountMovementRepository extends JpaRepository<AccountMovement, Long> {

    @Query("SELECT m FROM AccountMovement m WHERE m.account.client.person.name = :name and m.created BETWEEN :startDate AND :endDate")
    Optional<List<AccountMovement>> findMovementsByDateAndClientName(@Param("name") String name, @Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);
}