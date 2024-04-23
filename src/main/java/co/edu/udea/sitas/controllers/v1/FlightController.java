package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.FlightDTO;
import co.edu.udea.sitas.domain.dto.ScaleDTO;
import co.edu.udea.sitas.domain.model.Flight;
import co.edu.udea.sitas.services.FlightService;
import co.edu.udea.sitas.services.ScaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v1/flights")
public class FlightController {

    private final FlightService flightService;
    private final ScaleService scaleService;

    @Autowired
    public FlightController(FlightService flightService, ScaleService scaleService) {
        this.flightService = flightService;
        this.scaleService = scaleService;
    }

    @GetMapping
    public ResponseEntity<List<FlightDTO>> findALL(@RequestParam Map<String, String> reqParam){
        List<FlightDTO> foundFlights;
        log.info("Parameters map: {}", reqParam);
        log.info("Search flights");
        foundFlights = flightService.findAll(reqParam).stream().map(FlightDTO::buildFlightDTO).toList();
        return ResponseEntity.ok(foundFlights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> findById(@PathVariable Long id){
        Optional<Flight> optionalFlight = flightService.findById(id);
        if(optionalFlight.isPresent()) {
            FlightDTO flightDTO = FlightDTO.buildFlightDTO(optionalFlight.get());
            return ResponseEntity.ok(flightDTO);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/scales")
    public ResponseEntity<List<ScaleDTO>> findScalesById(@PathVariable Long id){
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
