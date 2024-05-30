package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.FlightDTO;
import co.edu.udea.sitas.domain.dto.ScaleDTO;
import co.edu.udea.sitas.domain.model.Flight;
import co.edu.udea.sitas.services.FlightService;
import co.edu.udea.sitas.services.ScaleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Slf4j
@RestController
@RequestMapping("/v1/flights")
@Tag(name = "Flight", description = "The Flight API")
public class FlightController {

    private final FlightService flightService;
    private final ScaleService scaleService;

    @Autowired
    public FlightController(FlightService flightService, ScaleService scaleService) {
        this.flightService = flightService;
        this.scaleService = scaleService;
    }

    @Operation(summary = "Get all flights", description = "Returns a list of all flights based on search parameters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the flights", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FlightDTO.class))
            })
    })
    @GetMapping
    public ResponseEntity<List<FlightDTO>> findALL(@RequestParam Map<String, String> reqParam){
        List<FlightDTO> foundFlights;
        log.info("Parameters map: {}", reqParam);
        log.info("Search flights");
        foundFlights = flightService.findAll(reqParam).stream().map(FlightDTO::buildFlightDTO).toList();
        return ResponseEntity.ok(foundFlights);
    }

    @Operation(summary = "Get flight by ID", description = "Returns a single flight by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the flight", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FlightDTO.class))
            }),
            @ApiResponse(responseCode = "204", description = "No flight found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> findById(@Parameter(description = "ID of the flight") @PathVariable Long id){
        Optional<Flight> optionalFlight = flightService.findById(id);
        if(optionalFlight.isPresent()) {
            FlightDTO flightDTO = FlightDTO.buildFlightDTO(optionalFlight.get());
            return ResponseEntity.ok(flightDTO);
        }
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get scales by flight ID", description = "Returns a list of scales by flight ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the scales", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ScaleDTO.class))
            }),
            @ApiResponse(responseCode = "204", description = "No scales found")
    })
    @GetMapping("/{id}/scales")
    public ResponseEntity<List<ScaleDTO>> findScalesById(@Parameter(description = "ID of the flight") @PathVariable Long id){
        Optional<Flight> optionalFlight = flightService.findById(id);
        if(optionalFlight.isPresent()) {
            List<ScaleDTO> scales = scaleService
                    .findAllByFlight(optionalFlight.get())
                    .stream().map(ScaleDTO::buildScaleDTO).toList();
            return ResponseEntity.ok(scales);
        }
        return ResponseEntity.noContent().build();
    }
}

