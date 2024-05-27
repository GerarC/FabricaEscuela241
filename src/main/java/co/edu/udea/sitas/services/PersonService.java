package co.edu.udea.sitas.services;

import co.edu.udea.sitas.domain.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> findAll();
    Person save(Person person);
    Optional<Person> findById(Long id);
    Optional<Person> update(Long id, Person person);
    Optional<Person> delete(Long id);
}

