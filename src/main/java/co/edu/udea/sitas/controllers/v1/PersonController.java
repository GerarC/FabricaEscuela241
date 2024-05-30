package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.PersonDTO;
import co.edu.udea.sitas.domain.model.IdentificationType;
import co.edu.udea.sitas.domain.model.Person;
import co.edu.udea.sitas.services.IdentificationTypeService;
import co.edu.udea.sitas.services.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/v1/people")
@Tag(name = "Person", description = "The Person API")
public class PersonController {

    private final IdentificationTypeService identificationTypeService;
    private final PersonService personService;

    @Autowired
    public PersonController(IdentificationTypeService identificationTypeService, PersonService personService) {
        this.identificationTypeService = identificationTypeService;
        this.personService = personService;
    }

    @Operation(summary = "Get all persons", description = "Returns a list of all persons.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the persons", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))
            })
    })
    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        List<PersonDTO> personDTOs = personService.findAll().stream()
                .map(PersonDTO::buildPersonDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(personDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Get person by ID", description = "Returns a single person by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the person", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@Parameter(description = "ID of the person") @PathVariable Long id) {
        Optional<Person> personOptional = personService.findById(id);
        return personOptional.map(person -> new ResponseEntity<>(PersonDTO.buildPersonDTO(person), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Create a new person", description = "Creates a new person.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Person created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))
            })
    })
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

    @Operation(summary = "Update an existing person", description = "Updates an existing person by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PersonDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(
            @Parameter(description = "ID of the person") @PathVariable Long id,
            @RequestBody PersonDTO personDTO) {
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


