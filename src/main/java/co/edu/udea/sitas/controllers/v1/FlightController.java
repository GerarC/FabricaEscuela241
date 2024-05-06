package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.FlightDTO;
import co.edu.udea.sitas.domain.dto.ScaleDTO;
import co.edu.udea.sitas.domain.model.Flight;
import co.edu.udea.sitas.hateoas.FlightHateoasAssembler;
import co.edu.udea.sitas.hateoas.ScaleHateoasAssembler;
import co.edu.udea.sitas.services.FlightService;
import co.edu.udea.sitas.services.ScaleService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v1/flights")
@Transactional
public class FlightController {

    private final FlightService flightService;
    private final ScaleService scaleService;
    private final FlightHateoasAssembler flightHateoasAssembler;
    private final ScaleHateoasAssembler scaleHateoasAssembler;

    @Autowired
    public FlightController(FlightService flightService, ScaleService scaleService, FlightHateoasAssembler flightHateoasAssembler, ScaleHateoasAssembler scaleHateoasAssembler) {
        this.flightService = flightService;
        this.scaleService = scaleService;
        this.flightHateoasAssembler = flightHateoasAssembler;
        this.scaleHateoasAssembler = scaleHateoasAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<FlightDTO>> findALL(@RequestParam Map<String, String> reqParam){
        CollectionModel<FlightDTO> foundFlights;
        log.info("Parameters map: {}", reqParam);
        log.info("Search flights");
        foundFlights = flightHateoasAssembler.toCollectionModel(flightService.findAll(reqParam));
        return ResponseEntity.ok(foundFlights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> findById(@PathVariable Long id){
        Optional<Flight> optionalFlight = flightService.findById(id);
        if(optionalFlight.isPresent()) {
            FlightDTO flightDTO = flightHateoasAssembler.toModel(optionalFlight.get());
            return ResponseEntity.ok(flightDTO);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/scales")
    public ResponseEntity<CollectionModel<ScaleDTO>> findScalesById(@PathVariable Long id){
        Optional<Flight> optionalFlight = flightService.findById(id);
        if(optionalFlight.isPresent()) {
            CollectionModel<ScaleDTO> scales = scaleHateoasAssembler.toCollectionModel(scaleService
                    .findAllByFlight(optionalFlight.get()));
            return ResponseEntity.ok(scales);
        }
        return ResponseEntity.noContent().build();
    }
}
