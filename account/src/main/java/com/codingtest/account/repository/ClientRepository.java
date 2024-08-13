package com.codingtest.account.repository;

import com.codingtest.account.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c JOIN c.person p  WHERE p.identification = :identification")
    public Optional<Client> findByIdentification(@Param("identification") String identification);

    @Query("SELECT c FROM Client c JOIN c.person p  WHERE p.name = :name")
    public Optional<Client> findByName(@Param("name") String name);
}