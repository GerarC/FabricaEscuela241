package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.AirportDTO;
import co.edu.udea.sitas.domain.model.Airport;
import co.edu.udea.sitas.services.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/airports")
@Tag(name = "Airport", description = "The Airport API")
public class AirportController {

    private final AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @Operation(summary = "Get all airports", description = "Returns a list of all airports.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the airports", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AirportDTO.class))
            })
    })
    @GetMapping
    public ResponseEntity<List<AirportDTO>> getAllAirports() {
        List<AirportDTO> airportDTOs = airportService.findAll().stream()
                .map(AirportDTO::buildAirportDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(airportDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Get airport by code", description = "Returns a single airport by its code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the airport", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AirportDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Airport not found")
    })
    @GetMapping("/{airportCode}")
    public ResponseEntity<AirportDTO> getAirportByCode(@Parameter(description = "Code of the airport") @PathVariable String airportCode) {
        Optional<Airport> airportOptional = airportService.findById(airportCode);
        return airportOptional.map(airport -> new ResponseEntity<>(AirportDTO.buildAirportDTO(airport), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Create a new airport", description = "Creates a new airport.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Airport created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AirportDTO.class))
            })
    })
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

    @Operation(summary = "Update an existing airport", description = "Updates an existing airport by its code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airport updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AirportDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Airport not found")
    })
    @PutMapping("/{airportCode}")
    public ResponseEntity<AirportDTO> updateAirport(
            @Parameter(description = "Code of the airport") @PathVariable String airportCode,
            @RequestBody AirportDTO airportDTO) {
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
