package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.FlightHistoryDTO;
import co.edu.udea.sitas.domain.model.Flight;
import co.edu.udea.sitas.domain.model.FlightHistory;
import co.edu.udea.sitas.domain.model.Person;
import co.edu.udea.sitas.services.FlightHistoryService;
import co.edu.udea.sitas.services.FlightService;
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
@RequestMapping("/v1/flight-histories")
@Tag(name = "Flight History", description = "The Flight History API")
public class FlightHistoryController {

    private final FlightHistoryService flightHistoryService;
    private final PersonService personService;
    private final FlightService flightService;

    @Autowired
    public FlightHistoryController(FlightHistoryService flightHistoryService, PersonService personService, FlightService flightService) {
        this.flightHistoryService = flightHistoryService;
        this.personService = personService;
        this.flightService = flightService;
    }

    @Operation(summary = "Get all flight histories", description = "Returns a list of all flight histories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the flight histories", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FlightHistoryDTO.class))
            })
    })
    @GetMapping
    public ResponseEntity<List<FlightHistoryDTO>> getAllFlightHistories() {
        List<FlightHistoryDTO> flightHistoryDTOs = flightHistoryService.findAll().stream()
                .map(FlightHistoryDTO::buildFlightHistoryDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(flightHistoryDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Get flight history by ID", description = "Returns a single flight history by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the flight history", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FlightHistoryDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Flight history not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FlightHistoryDTO> getFlightHistoryById(@Parameter(description = "ID of the flight history") @PathVariable Long id) {
        Optional<FlightHistory> flightHistoryOptional = flightHistoryService.findById(id);
        return flightHistoryOptional.map(flightHistory -> new ResponseEntity<>(FlightHistoryDTO.buildFlightHistoryDTO(flightHistory), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Create a new flight history", description = "Creates a new flight history.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Flight history created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FlightHistoryDTO.class))
            })
    })
    @PostMapping
    public ResponseEntity<FlightHistoryDTO> createFlightHistory(@RequestBody FlightHistoryDTO flightHistoryDTO) {
        Flight flight = flightService.findById(flightHistoryDTO.getFlightId()).orElseThrow();
        Person person = personService.findById(flightHistoryDTO.getPersonId()).orElseThrow();
        FlightHistory flightHistory = FlightHistory.builder()
                .person(person)
                .flight(flight)
                .build();
        FlightHistory savedFlightHistory = flightHistoryService.save(flightHistory);
        return new ResponseEntity<>(FlightHistoryDTO.buildFlightHistoryDTO(savedFlightHistory), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing flight history", description = "Updates an existing flight history by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight history updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FlightHistoryDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Flight history not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<FlightHistoryDTO> updateFlightHistory(
            @Parameter(description = "ID of the flight history") @PathVariable Long id,
            @RequestBody FlightHistoryDTO flightHistoryDTO) {
        Flight flight = flightService.findById(flightHistoryDTO.getFlightId()).orElseThrow();
        Person person = personService.findById(flightHistoryDTO.getPersonId()).orElseThrow();
        Optional<FlightHistory> flightHistoryOptional = flightHistoryService.update(id, FlightHistory.builder()
                .person(person)
                .flight(flight)
                .build());
        return flightHistoryOptional.map(flightHistory -> new ResponseEntity<>(FlightHistoryDTO.buildFlightHistoryDTO(flightHistory), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}


