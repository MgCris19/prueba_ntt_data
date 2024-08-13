package com.codingtest.account.mapper;

import com.codingtest.account.dto.ClientDto;
import com.codingtest.account.dto.PersonDto;
import com.codingtest.account.entity.Client;
import com.codingtest.account.entity.Person;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {

    public static ClientDto toDto(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .person(PersonDto.builder()
                        .id(client.getPerson().getId())
                        .age(client.getPerson().getAge())
                        .address(client.getPerson().getAddress())
                        .name(client.getPerson().getName())
                        .phone(client.getPerson().getPhone())
                        .gender(client.getPerson().getGender())
                        .identification(client.getPerson().getIdentification())
                        .build())
                .password(client.getPassword())
                .status(client.getStatus())
                .build();
    }

    public static Client toEntity(ClientDto clientDto) {
        return Client.builder()
                .person(Person.builder()
                        .age(clientDto.getPerson().getAge())
                        .address(clientDto.getPerson().getAddress())
                        .name(clientDto.getPerson().getName())
                        .phone(clientDto.getPerson().getPhone())
                        .gender(clientDto.getPerson().getGender())
                        .identification(clientDto.getPerson().getIdentification())
                        .build())
                .password(clientDto.getPassword())
                .status(clientDto.getStatus())
                .build();
    }
}
