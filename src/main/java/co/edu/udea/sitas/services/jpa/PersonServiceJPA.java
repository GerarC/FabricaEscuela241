package co.edu.udea.sitas.services.jpa;

import co.edu.udea.sitas.domain.model.Person;
import co.edu.udea.sitas.persistence.PersonRepository;
import co.edu.udea.sitas.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceJPA implements PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceJPA(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public Optional<Person> update(Long id, Person person) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isPresent()) {
            person.setPersonId(id);
            return Optional.of(personRepository.save(person));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Person> delete(Long id) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isPresent()) {
            personRepository.deleteById(id);
        }
        return personOptional;
    }
}

