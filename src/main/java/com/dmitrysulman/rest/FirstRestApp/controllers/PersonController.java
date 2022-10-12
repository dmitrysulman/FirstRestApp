package com.dmitrysulman.rest.FirstRestApp.controllers;

import com.dmitrysulman.rest.FirstRestApp.models.Person;
import com.dmitrysulman.rest.FirstRestApp.services.PersonService;
import com.dmitrysulman.rest.FirstRestApp.util.PersonErrorResponse;
import com.dmitrysulman.rest.FirstRestApp.util.PersonNotCreatedException;
import com.dmitrysulman.rest.FirstRestApp.util.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException personNotCreatedException) {
        PersonErrorResponse personErrorResponse = new PersonErrorResponse(
                personNotCreatedException.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(personErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid Person person,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMsg.append(fieldError.getField())
                        .append(": ")
                        .append(fieldError.getDefaultMessage())
                        .append(";");
            }

            throw new PersonNotCreatedException(errorMsg.toString());
        }
        personService.save(person);

        return ResponseEntity.noContent().build();
    }
}
