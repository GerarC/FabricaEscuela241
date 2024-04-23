package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.AirportDTO;
import co.edu.udea.sitas.domain.model.Airport;
import co.edu.udea.sitas.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/airports")
public class AirportController {

    private final AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public ResponseEntity<List<AirportDTO>> getAllAirports() {
        List<AirportDTO> airportDTOs = airportService.findAll().stream()
                .map(AirportDTO::buildAirportDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(airportDTOs, HttpStatus.OK);
    }

    @GetMapping("/{airportCode}")
    public ResponseEntity<AirportDTO> getAirportByCode(@PathVariable String airportCode) {
        Optional<Airport> airportOptional = airportService.findById(airportCode);
        return airportOptional.map(airport -> new ResponseEntity<>(AirportDTO.buildAirportDTO(airport), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<AirportDTO> createAirport(@RequestBody AirportDTO airportDTO) {
        Airport airport = Airport.builder()
                .airportCode(airportDTO.getAirportCode())
                .name(airportDTO.getName())
                .type(airportDTO.getType())
                .city(airportDTO.getCity())
                .country(airportDTO.getCountry())
                .runways(airportDTO.getRunways())
                .build();
        Airport savedAirport = airportService.save(airport);
        return new ResponseEntity<>(AirportDTO.buildAirportDTO(savedAirport), HttpStatus.CREATED);
    }

    @PutMapping("/{airportCode}")
    public ResponseEntity<AirportDTO> updateAirport(@PathVariable String airportCode, @RequestBody AirportDTO airportDTO) {
        Optional<Airport> airportOptional = airportService.update(airportCode, Airport.builder()
                .airportCode(airportDTO.getAirportCode())
                .name(airportDTO.getName())
                .type(airportDTO.getType())
                .city(airportDTO.getCity())
                .country(airportDTO.getCountry())
                .runways(airportDTO.getRunways())
                .build());
        return airportOptional.map(airport -> new ResponseEntity<>(AirportDTO.buildAirportDTO(airport), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
