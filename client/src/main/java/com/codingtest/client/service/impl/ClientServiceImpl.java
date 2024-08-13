package com.codingtest.client.service.impl;

import com.codingtest.client.dto.ClientDto;
import com.codingtest.client.entity.Client;
import com.codingtest.client.entity.Person;
import com.codingtest.client.exception.ResourceFoundException;
import com.codingtest.client.exception.ResourceNotFoundException;
import com.codingtest.client.mapper.ClientMapper;
import com.codingtest.client.mapper.PersonMapper;
import com.codingtest.client.repository.ClientRepository;
import com.codingtest.client.repository.PersonRepository;
import com.codingtest.client.service.ClientService;
import com.codingtest.client.utils.Messages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final PersonRepository personRepository;

    private final ApiServiceImpl apiService;

    @Value("${api.inactiveAccountApi}")
    private String inactiveAccountApi;

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream().map(ClientMapper::toDto).toList();
    }
    @Override
    public ClientDto findById(Long id) throws ResourceNotFoundException {
        var client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Messages.CLIENT_NOT_FOUND));

        return ClientMapper.toDto(client);
    }
    @Override
    public ClientDto save(ClientDto clientDto) throws ResourceFoundException {
        int count = 0;
        //se usa nombre porque me estoy basando en los casos de uso, esto no es lo ideal
        Person person = personRepository.findPersonByName(clientDto.getPerson().getName()).orElse(null);

        if (person == null) {
            person = personRepository.save(PersonMapper.toEntity(clientDto.getPerson()));

        }
        count = clientRepository.countActiveClient(person.getId());

        if (count != 0) {
           throw new ResourceFoundException(Messages.CLIENT_ACTIVE);
        }

        var client = ClientMapper.toEntity(clientDto);

        client.setPerson(person);

        return ClientMapper.toDto(clientRepository.save(client));
    }
    @Override
    public ClientDto deleteById(Long id) throws ResourceNotFoundException{
        clientRepository.deleteById(id);
        var client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Messages.CLIENT_NOT_FOUND));

        clientRepository.deleteById(id);

        return ClientMapper.toDto(client);
    }
    @Override
    public ClientDto findByIdentification(String identification) throws ResourceNotFoundException {
        var client = clientRepository.findByIdentification(identification).orElseThrow(() -> new ResourceNotFoundException(Messages.CLIENT_NOT_FOUND));

        return ClientMapper.toDto(client);
    }
    @Override
    public ClientDto findByName(String name) throws ResourceNotFoundException{
        var client = clientRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException(Messages.CLIENT_NOT_FOUND));

        return ClientMapper.toDto(client);
    }
    @Override
    public ClientDto deleteClient(String name,Long id) throws ResourceNotFoundException{

            Client existingClient = name!=null
                    ?clientRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException(Messages.CLIENT_NOT_FOUND))
                    :clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Messages.CLIENT_NOT_FOUND));

            existingClient.setStatus(Boolean.FALSE);
            existingClient.setPerson(personRepository.save(existingClient.getPerson()));

            var client = clientRepository.save(existingClient);

            apiService.callDeleteService(inactiveAccountApi+client.getPerson().getName())
                        .doOnSuccess(res -> handleSuccess(client.getId()))
                        .doOnError(error -> handleError(client.getId(), error))
                        .subscribe();

            return ClientMapper.toDto(client);

    }

    @Override
    public ClientDto updateClient(ClientDto client) throws ResourceNotFoundException{

        Client existingClient = clientRepository
                .findByName(client.getPerson().getName())
                .orElseThrow(() -> new ResourceNotFoundException(Messages.CLIENT_NOT_FOUND));;

        existingClient.getPerson().setIdentification(client.getPerson().getIdentification());
        existingClient.getPerson().setGender(client.getPerson().getGender());
        existingClient.getPerson().setAge(client.getPerson().getAge());
        existingClient.getPerson().setPhone(client.getPerson().getPhone());
        existingClient.getPerson().setAddress(client.getPerson().getAddress());
        existingClient.setStatus(client.getStatus());
        existingClient.setPassword(client.getPassword());

        return ClientMapper.toDto(clientRepository.save(existingClient));
    }

    private void handleSuccess(Long clientId) {
        // Lógica para manejar la desactivación exitosa de las cuentas
        log.info("Cuentas desactivadas con éxito para el cliente: {}", clientId);
    }

    private void handleError(Long clientId, Throwable error) {
        // Lógica para manejar errores en la desactivación de las cuentas
        log.info("Error al desactivar las cuentas para el cliente: {}. Error: {}", clientId, error.getMessage());
    }
}