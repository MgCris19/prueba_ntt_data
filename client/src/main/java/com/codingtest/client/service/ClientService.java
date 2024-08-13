package com.codingtest.client.service;

import com.codingtest.client.dto.ClientDto;
import com.codingtest.client.entity.Client;
import com.codingtest.client.exception.ResourceFoundException;
import com.codingtest.client.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClientService {

    List<ClientDto> findAll();
    ClientDto findById(Long id) throws ResourceNotFoundException;
    ClientDto save(ClientDto client) throws ResourceFoundException;
    ClientDto deleteById(Long id) throws ResourceNotFoundException;
    ClientDto findByIdentification(String identification) throws ResourceNotFoundException;
    ClientDto findByName(String name) throws ResourceNotFoundException;
    ClientDto deleteClient(String name,Long id) throws ResourceNotFoundException;
    ClientDto updateClient(ClientDto client) throws ResourceNotFoundException;
}