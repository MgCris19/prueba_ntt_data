package com.codingtest.client.controller;

import com.codingtest.client.model.Client;
import com.codingtest.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Long id) {
        return clientService.findById(id);
    }

    @GetMapping("/identification/{identification}")
    public Client getClientById(@PathVariable String identification) {
        return clientService.findByIdentification(identification);
    }

    @PostMapping
    public ResponseEntity<?> createClient(@RequestBody Client client) {
        return clientService.save(client);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateClient(@RequestBody Client client) {
        return clientService.updateClient(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(null,id);
    }

    @DeleteMapping("/nombre/{name}")
    public ResponseEntity<?> deleteClientByName(@PathVariable String name) {
       return clientService.deleteClient(name,null);
    }

}
