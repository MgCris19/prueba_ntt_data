package com.codingtest.account.mapper;

import com.codingtest.account.dto.PersonDto;
import com.codingtest.account.entity.Person;

public class PersonMapper {

    public static PersonDto toDto(Person person) {
        return PersonDto.builder()
                        .age(person.getAge())
                        .address(person.getAddress())
                        .name(person.getName())
                        .phone(person.getPhone())
                        .gender(person.getGender())
                        .identification(person.getIdentification())
                        .build();
    }

    public static Person toEntity(PersonDto personDto) {
        return Person.builder()
                .age(personDto.getAge())
                .address(personDto.getAddress())
                .name(personDto.getName())
                .phone(personDto.getPhone())
                .gender(personDto.getGender())
                .identification(personDto.getIdentification())
                .build();
    }
}