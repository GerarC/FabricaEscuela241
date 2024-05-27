package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.FlightHistoryDTO;
import co.edu.udea.sitas.domain.model.Flight;
import co.edu.udea.sitas.domain.model.FlightHistory;
import co.edu.udea.sitas.domain.model.Person;
import co.edu.udea.sitas.services.FlightHistoryService;
import co.edu.udea.sitas.services.FlightService;
import co.edu.udea.sitas.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/flight-histories")
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

    @GetMapping
    public ResponseEntity<List<FlightHistoryDTO>> getAllFlightHistories() {
        List<FlightHistoryDTO> flightHistoryDTOs = flightHistoryService.findAll().stream()
                .map(FlightHistoryDTO::buildFlightHistoryDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(flightHistoryDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightHistoryDTO> getFlightHistoryById(@PathVariable Long id) {
        Optional<FlightHistory> flightHistoryOptional = flightHistoryService.findById(id);
        return flightHistoryOptional.map(flightHistory -> new ResponseEntity<>(FlightHistoryDTO.buildFlightHistoryDTO(flightHistory), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<FlightHistoryDTO> updateFlightHistory(@PathVariable Long id, @RequestBody FlightHistoryDTO flightHistoryDTO) {
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

