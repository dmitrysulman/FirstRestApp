package com.dmitrysulman.rest.FirstRestApp.repositories;

import com.dmitrysulman.rest.FirstRestApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
