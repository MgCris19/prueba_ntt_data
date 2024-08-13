package com.codingtest.client.controller;

import com.codingtest.client.dto.request.ClientRequest;
import com.codingtest.client.dto.response.ClientResponse;
import com.codingtest.client.dto.response.ClientsResponse;
import com.codingtest.client.dto.response.Response;
import com.codingtest.client.exception.ResourceFoundException;
import com.codingtest.client.exception.ResourceNotFoundException;
import com.codingtest.client.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public Response<ClientsResponse, Error> getAllClients() {
        var clients = clientService.findAll();

        var response = ClientsResponse.builder().clients(clients).build();

        return Response.ok(response);
    }

    @GetMapping("/{id}")
    public Response<ClientResponse, Error> getClientById(@PathVariable Long id) throws ResourceNotFoundException {
        var client = clientService.findById(id);

        var response = ClientResponse.builder().clientDto(client).build();

        return Response.ok(response);
    }

    @GetMapping("/identification/{identification}")
    public Response<ClientResponse, Error> getClientById(@PathVariable String identification) throws ResourceNotFoundException {
        var client = clientService.findByIdentification(identification);

        var response = ClientResponse.builder().clientDto(client).build();

        return Response.ok(response);
    }

    @PostMapping
    public Response<ClientResponse, Error> createClient(@RequestBody @Valid ClientRequest clientRequest) throws ResourceFoundException {
        var clients = clientService.save(clientRequest.getClientDto());

        var response = ClientResponse.builder().clientDto(clients).build();

        return Response.ok(response);
    }

    @PutMapping("/")
    public Response<ClientResponse, Error> updateClient(@RequestBody @Valid ClientRequest clientRequest) throws ResourceNotFoundException {
        var clients = clientService.updateClient(clientRequest.getClientDto());

        var response = ClientResponse.builder().clientDto(clients).build();

        return Response.ok(response);

    }

    @DeleteMapping("/{id}")
    public Response<ClientResponse, Error> deleteClient(@PathVariable Long id) throws ResourceNotFoundException {

        var clients = clientService.deleteClient(null,id);

        var response = ClientResponse.builder().clientDto(clients).build();

        return Response.ok(response);
    }

    @DeleteMapping("/nombre/{name}")
    public Response<ClientResponse, Error> deleteClientByName(@PathVariable String name) throws ResourceNotFoundException {
        var clients = clientService.deleteClient(name,null);

        var response = ClientResponse.builder().clientDto(clients).build();

        return Response.ok(response);
    }

}
