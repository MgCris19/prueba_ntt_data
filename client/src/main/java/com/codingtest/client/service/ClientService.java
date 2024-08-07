package com.codingtest.client.service;

import com.codingtest.client.config.Logging;
import com.codingtest.client.dto.ApiResponse;
import com.codingtest.client.model.Client;
import com.codingtest.client.model.Person;
import com.codingtest.client.repository.ClientRepository;
import com.codingtest.client.repository.PersonRepository;
import com.codingtest.client.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    private final PersonRepository personRepository;

    private final ApiService apiService;
    @Value("${api.inactiveAccountApi}")
    private String inactiveAccountApi;
    @Autowired
    public ClientService(ClientRepository clientRepository,
                         PersonRepository personRepository,
                         ApiService apiService) {
        this.clientRepository = clientRepository;
        this.personRepository = personRepository;
        this.apiService = apiService;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

//    public ResponseEntity<?> save(Client client) {
//        try {
//            Person person = personRepository.findPersonByIdentification(client.getPerson().getIdentification()).orElse(null);
//
//            if (person == null) {
//                person = personRepository.save(client.getPerson());
//
//            }
//            client.setPerson(person);
//            return ResponseEntity.ok(clientRepository.save(client));
//        }catch (Exception e){
//            ApiResponse response = new ApiResponse();
//            response.setMessage(e.getMessage());
//            response.setError(new ApiResponse.Error(Messages.ERROR, e.getMessage()));
//            response.setSuccess(Boolean.FALSE);
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

        public ResponseEntity<?> save(Client client) {
            ApiResponse response = new ApiResponse();
        try {
            //se usa nombre porque me estoy basando en los casos de uso, esto no es lo ideal
            Person person = personRepository.findPersonByName(client.getPerson().getName()).orElse(null);
            int count = 0;

            if (person == null) {
                person = personRepository.save(client.getPerson());

            }

            count = clientRepository.countActiveClient(person.getId());

            if (count != 0) {
                response.setMessage(Messages.CLIENT_ACTIVE);
                response.setError(new ApiResponse.Error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), Messages.CLIENT_ACTIVE));
                response.setSuccess(Boolean.FALSE);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            client.setPerson(person);
            return ResponseEntity.ok(clientRepository.save(client));
        }catch (Exception e){
            response.setMessage(e.getMessage());
            response.setError(new ApiResponse.Error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage()));
            response.setSuccess(Boolean.FALSE);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }
    public Client findByIdentification(String identification) {
        return clientRepository.findByIdentification(identification).orElse(null);
    }

    public Client findByName(String name) {
        return clientRepository.findByName(name).orElse(null);
    }

    public ResponseEntity<?> deleteClient(String name,Long id) {
        ApiResponse response = new ApiResponse();
        try {
            Client existingClient = name!=null
                    ?findByName(name)
                    :findById(id);
            if (existingClient != null) {
                existingClient.setStatus(Boolean.FALSE);
                response.setSuccess(Boolean.TRUE);
                response.setData(clientRepository.save(existingClient));
                apiService.callDeleteService(inactiveAccountApi+existingClient.getPerson().getName())
                        .doOnSuccess(res -> handleSuccess(existingClient.getId()))
                        .doOnError(error -> handleError(existingClient.getId(), error))
                        .subscribe();

                return ResponseEntity.ok(response);
            } else {
                response.setMessage(Messages.CLIENT_NOT_FOUND);
                response.setError(new ApiResponse.Error(String.valueOf(HttpStatus.NOT_FOUND.value()), Messages.CLIENT_NOT_FOUND));
                response.setSuccess(Boolean.FALSE);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            Logging.error(e.getMessage());
            response.setMessage(e.getMessage());
            response.setError(new ApiResponse.Error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage()));
            response.setSuccess(Boolean.FALSE);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> updateClient(Client client) {
        ApiResponse response = new ApiResponse();

        try {
            Client existingClient = findByName(client.getPerson().getName());

            if (existingClient != null) {
                Person existingPerson = existingClient.getPerson();

                existingPerson.setIdentification(client.getPerson().getIdentification());
                existingPerson.setGender(client.getPerson().getGender());
                existingPerson.setAge(client.getPerson().getAge());
                existingPerson.setPhone(client.getPerson().getPhone());
                existingPerson.setAddress(client.getPerson().getAddress());

                existingClient.setStatus(client.getStatus());
                existingClient.setPerson(existingPerson);
                existingClient.setPassword(client.getPassword());
                response.setSuccess(Boolean.TRUE);
                response.setData(clientRepository.save(existingClient));
                return ResponseEntity.ok(response);
            } else {
                response.setMessage(Messages.CLIENT_NOT_FOUND);
                response.setError(new ApiResponse.Error(String.valueOf(HttpStatus.NOT_FOUND.value()), Messages.CLIENT_NOT_FOUND));
                response.setSuccess(Boolean.FALSE);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            Logging.error(e.getMessage());
            response.setMessage(e.getMessage());
            response.setError(new ApiResponse.Error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getMessage()));
            response.setSuccess(Boolean.FALSE);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void handleSuccess(Long clientId) {
        // Lógica para manejar la desactivación exitosa de las cuentas
        Logging.info("Cuentas desactivadas con éxito para el cliente: " + clientId);
    }

    private void handleError(Long clientId, Throwable error) {
        // Lógica para manejar errores en la desactivación de las cuentas
        Logging.info("Error al desactivar las cuentas para el cliente: " + clientId + ". Error: " + error.getMessage());
    }
}