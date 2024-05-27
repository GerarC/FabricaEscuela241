package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.PersonDTO;
import co.edu.udea.sitas.domain.model.IdentificationType;
import co.edu.udea.sitas.domain.model.Person;
import co.edu.udea.sitas.services.IdentificationTypeService;
import co.edu.udea.sitas.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonControllerTest {

    @Mock
    private PersonService personService;

    @Mock
    private IdentificationTypeService identificationTypeService;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPersons() {
        // Arrange
        IdentificationType identificationType = new IdentificationType();
        identificationType.setIdentificationTypeId(1L);

        Person person1 = new Person();
        person1.setIdentificationType(identificationType);

        Person person2 = new Person();
        person2.setIdentificationType(identificationType);

        List<Person> persons = Arrays.asList(person1, person2);

        when(personService.findAll()).thenReturn(persons);

        // Act
        ResponseEntity<List<PersonDTO>> response = personController.getAllPersons();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(personService, times(1)).findAll();
    }

    @Test
    void getPersonById() {
        // Arrange
        Long id = 1L;
        IdentificationType identificationType = new IdentificationType();
        identificationType.setIdentificationTypeId(1L);

        Person person = new Person();
        person.setIdentificationType(identificationType);

        when(personService.findById(id)).thenReturn(Optional.of(person));

        // Act
        ResponseEntity<PersonDTO> response = personController.getPersonById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(personService, times(1)).findById(id);
    }

    @Test
    void createPerson() {
        // Arrange
        PersonDTO requestDto = new PersonDTO();
        requestDto.setIdentificationTypeId(1L);

        IdentificationType identificationType = new IdentificationType();
        identificationType.setIdentificationTypeId(1L);

        Person person = new Person();
        person.setIdentificationType(identificationType);

        when(identificationTypeService.findById(requestDto.getIdentificationTypeId())).thenReturn(Optional.of(identificationType));
        when(personService.save(any())).thenReturn(person);

        // Act
        ResponseEntity<PersonDTO> response = personController.createPerson(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(personService, times(1)).save(any());
    }

    @Test
    void updatePerson() {
        // Arrange
        Long id = 1L;
        PersonDTO requestDto = new PersonDTO();
        requestDto.setIdentificationTypeId(1L);

        IdentificationType identificationType = new IdentificationType();
        identificationType.setIdentificationTypeId(1L);

        Person person = new Person();
        person.setIdentificationType(identificationType);

        when(identificationTypeService.findById(requestDto.getIdentificationTypeId())).thenReturn(Optional.of(identificationType));
        when(personService.update(eq(id), any())).thenReturn(Optional.of(person));

        // Act
        ResponseEntity<PersonDTO> response = personController.updatePerson(id, requestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(personService, times(1)).update(eq(id), any());
    }
}


