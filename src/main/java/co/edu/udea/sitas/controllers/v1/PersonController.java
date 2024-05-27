package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.PersonDTO;
import co.edu.udea.sitas.domain.model.IdentificationType;
import co.edu.udea.sitas.domain.model.Person;
import co.edu.udea.sitas.services.IdentificationTypeService;
import co.edu.udea.sitas.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/people")
public class PersonController {

    private final IdentificationTypeService identificationTypeService;

    private final PersonService personService;

    @Autowired
    public PersonController(IdentificationTypeService identificationTypeService, PersonService personService) {
        this.identificationTypeService = identificationTypeService;
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        List<PersonDTO> personDTOs = personService.findAll().stream()
                .map(PersonDTO::buildPersonDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(personDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        Optional<Person> personOptional = personService.findById(id);
        return personOptional.map(person -> new ResponseEntity<>(PersonDTO.buildPersonDTO(person), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO) {
        IdentificationType identificationType = identificationTypeService.findById(personDTO.getIdentificationTypeId()).orElseThrow();
        Person person = Person.builder()
                .identificationType(identificationType)
                .identificationNumber(personDTO.getIdentificationNumber())
                .firstName(personDTO.getFirstName())
                .lastName(personDTO.getLastName())
                .genre(personDTO.getGenre())
                .birthDate(personDTO.getBirthDate())
                .phoneNumber(personDTO.getPhoneNumber())
                .country(personDTO.getCountry())
                .province(personDTO.getProvince())
                .city(personDTO.getCity())
                .residence(personDTO.getResidence())
                .email(personDTO.getEmail())
                .accessKey(personDTO.getAccessKey())
                .build();
        Person savedPerson = personService.save(person);
        return new ResponseEntity<>(PersonDTO.buildPersonDTO(savedPerson), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable Long id, @RequestBody PersonDTO personDTO) {
        IdentificationType identificationType = identificationTypeService.findById(personDTO.getIdentificationTypeId()).orElseThrow();
        Optional<Person> personOptional = personService.update(id, Person.builder()
                .identificationType(identificationType)
                .identificationNumber(personDTO.getIdentificationNumber())
                .firstName(personDTO.getFirstName())
                .lastName(personDTO.getLastName())
                .genre(personDTO.getGenre())
                .birthDate(personDTO.getBirthDate())
                .phoneNumber(personDTO.getPhoneNumber())
                .country(personDTO.getCountry())
                .province(personDTO.getProvince())
                .city(personDTO.getCity())
                .residence(personDTO.getResidence())
                .email(personDTO.getEmail())
                .accessKey(personDTO.getAccessKey())
                .build());
        return personOptional.map(person -> new ResponseEntity<>(PersonDTO.buildPersonDTO(person), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

