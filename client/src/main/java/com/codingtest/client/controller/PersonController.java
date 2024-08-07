package com.codingtest.client.controller;

import com.codingtest.client.model.Person;
import com.codingtest.client.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> getAllPersons() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable Long id) {
        return personService.findById(id);
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personService.save(person);
    }

    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable Long id, @RequestBody Person person) {
        Person existingPerson = personService.findById(id);
        if (existingPerson != null) {
            existingPerson.setName(person.getName());
            existingPerson.setAddress(person.getAddress());
            existingPerson.setAge(person.getAge());
            existingPerson.setGender(person.getGender());
            existingPerson.setIdentification(person.getIdentification());
            existingPerson.setPhone(person.getPhone());
            return personService.save(existingPerson);
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.delete(id);
    }

}
