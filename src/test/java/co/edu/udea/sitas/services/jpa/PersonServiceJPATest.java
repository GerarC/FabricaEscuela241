package co.edu.udea.sitas.services.jpa;

import co.edu.udea.sitas.domain.model.Person;
import co.edu.udea.sitas.persistence.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceJPATest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceJPA personServiceJPA;

    @Test
    void testFindAll() {
        // Arrange
        List<Person> persons = new ArrayList<>();
        when(personRepository.findAll()).thenReturn(persons);

        // Act
        List<Person> result = personServiceJPA.findAll();

        // Assert
        assertEquals(persons, result);
    }

    @Test
    void testSave() {
        // Arrange
        Person person = new Person();
        when(personRepository.save(person)).thenReturn(person);

        // Act
        Person result = personServiceJPA.save(person);

        // Assert
        assertEquals(person, result);
    }

    @Test
    void testFindById() {
        // Arrange
        Long id = 1L;
        Person person = new Person();
        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        // Act
        Optional<Person> result = personServiceJPA.findById(id);

        // Assert
        assertEquals(Optional.of(person), result);
    }

    @Test
    void testUpdate() {
        // Arrange
        Long id = 1L;
        Person person = new Person();
        when(personRepository.findById(id)).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(person);

        // Act
        Optional<Person> result = personServiceJPA.update(id, person);

        // Assert
        assertEquals(Optional.of(person), result);
    }

    @Test
    void testDelete() {
        // Arrange
        Long id = 1L;
        Person person = new Person();
        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        // Act
        Optional<Person> result = personServiceJPA.delete(id);

        // Assert
        assertEquals(Optional.of(person), result);
        verify(personRepository, times(1)).deleteById(id);
    }
}

