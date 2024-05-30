package co.edu.udea.sitas.controllers.v1;
import co.edu.udea.sitas.domain.dto.AirplaneModelDTO;
import co.edu.udea.sitas.domain.model.AirplaneModel;
import co.edu.udea.sitas.services.AirplaneModelService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/airplane-models")
@Tag(name = "Airplane Model", description = "The Airplane Model API")
public class AirplaneModelController {

    private final AirplaneModelService airplaneModelService;

    @Autowired
    public AirplaneModelController(AirplaneModelService airplaneModelService) {
        this.airplaneModelService = airplaneModelService;
    }

    @Operation(summary = "Get all airplane models", description = "Returns a list of all airplane models.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the airplane models", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AirplaneModelDTO.class))
            })
    })
    @GetMapping
    public ResponseEntity<List<AirplaneModelDTO>> getAllAirplaneModels() {
        List<AirplaneModelDTO> airplaneModelDTOs = airplaneModelService.findAll().stream()
                .map(AirplaneModelDTO::buildAirplaneModelDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(airplaneModelDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Get airplane model by model name", description = "Returns a single airplane model by its model name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the airplane model", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AirplaneModelDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Airplane model not found")
    })
    @GetMapping("/{airplaneModel}")
    public ResponseEntity<AirplaneModelDTO> getAirplaneModelByModel(@Parameter(description = "Model name of the airplane") @PathVariable String airplaneModel) {
        Optional<AirplaneModel> airplaneModelOptional = airplaneModelService.findById(airplaneModel);
        return airplaneModelOptional.map(model -> new ResponseEntity<>(AirplaneModelDTO.buildAirplaneModelDTO(model), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Create a new airplane model", description = "Creates a new airplane model.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Airplane model created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AirplaneModelDTO.class))
            })
    })
    @PostMapping
    public ResponseEntity<AirplaneModelDTO> createAirplaneModel(@RequestBody AirplaneModelDTO airplaneModelDTO) {
        AirplaneModel airplaneModel = AirplaneModel.builder()
                .airplaneModel(airplaneModelDTO.getAirplaneModel())
                .family(airplaneModelDTO.getFamily())
                .capacity(airplaneModelDTO.getCapacity())
                .cargoCapacity(airplaneModelDTO.getCargoCapacity())
                .volumeCapacity(airplaneModelDTO.getVolumeCapacity())
                .build();
        AirplaneModel savedAirplaneModel = airplaneModelService.save(airplaneModel);
        return new ResponseEntity<>(AirplaneModelDTO.buildAirplaneModelDTO(savedAirplaneModel), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing airplane model", description = "Updates an existing airplane model by its model name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Airplane model updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AirplaneModelDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Airplane model not found")
    })
    @PutMapping("/{airplaneModel}")
    public ResponseEntity<AirplaneModelDTO> updateAirplaneModel(
            @Parameter(description = "Model name of the airplane") @PathVariable String airplaneModel,
            @RequestBody AirplaneModelDTO airplaneModelDTO) {
        Optional<AirplaneModel> airplaneModelOptional = airplaneModelService.update(airplaneModel, AirplaneModel.builder()
                .airplaneModel(airplaneModelDTO.getAirplaneModel())
                .family(airplaneModelDTO.getFamily())
                .capacity(airplaneModelDTO.getCapacity())
                .cargoCapacity(airplaneModelDTO.getCargoCapacity())
                .volumeCapacity(airplaneModelDTO.getVolumeCapacity())
                .build());
        return airplaneModelOptional.map(model -> new ResponseEntity<>(AirplaneModelDTO.buildAirplaneModelDTO(model), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
