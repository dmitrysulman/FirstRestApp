package com.dmitrysulman.rest.FirstRestApp.controllers;

import com.dmitrysulman.rest.FirstRestApp.models.Person;
import com.dmitrysulman.rest.FirstRestApp.services.PersonService;
import com.dmitrysulman.rest.FirstRestApp.util.PersonErrorResponse;
import com.dmitrysulman.rest.FirstRestApp.util.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping()
    public List<Person> getPeople() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") int id) {
        return personService.findById(id);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException personNotFoundException) {
        PersonErrorResponse personErrorResponse = new PersonErrorResponse("Person not found", System.currentTimeMillis());
        return new ResponseEntity<>(personErrorResponse, HttpStatus.NOT_FOUND);
    }
}
