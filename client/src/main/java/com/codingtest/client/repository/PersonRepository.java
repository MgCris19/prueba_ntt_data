package com.codingtest.client.repository;

import com.codingtest.client.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT p FROM Person p WHERE p.identification = :identification")
    public Optional<Person> findPersonByIdentification(@Param("identification") String identification);

    @Query("SELECT p FROM Person p WHERE p.name = :name")
    public Optional<Person> findPersonByName(@Param("name") String name);

}