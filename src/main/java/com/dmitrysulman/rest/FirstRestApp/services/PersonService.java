package com.dmitrysulman.rest.FirstRestApp.services;

import com.dmitrysulman.rest.FirstRestApp.models.Person;
import com.dmitrysulman.rest.FirstRestApp.repositories.PersonRepository;
import com.dmitrysulman.rest.FirstRestApp.util.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(int id) {
        return personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }
}
