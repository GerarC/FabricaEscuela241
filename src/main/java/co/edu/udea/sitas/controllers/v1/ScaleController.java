package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.ScaleDTO;
import co.edu.udea.sitas.domain.model.Scale;
import co.edu.udea.sitas.services.ScaleService;
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
@RequestMapping("/v1/scales")
@Tag(name = "Scale", description = "The Scale API")
public class ScaleController {

    private final ScaleService scaleService;

    @Autowired
    public ScaleController(ScaleService scaleService) {
        this.scaleService = scaleService;
    }

    @Operation(summary = "Get all scales", description = "Returns a list of all scales.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the scales", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ScaleDTO.class))
            })
    })
    @GetMapping
    public ResponseEntity<List<ScaleDTO>> getAllScales() {
        List<ScaleDTO> scaleDTOs = scaleService.findAll().stream()
                .map(ScaleDTO::buildScaleDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(scaleDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Get scale by ID", description = "Returns a single scale by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the scale", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ScaleDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Scale not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ScaleDTO> getScaleById(@Parameter(description = "ID of the scale") @PathVariable("id") Long id) {
        Optional<Scale> scaleOptional = scaleService.findById(id);
        return scaleOptional.map(scale -> new ResponseEntity<>(ScaleDTO.buildScaleDTO(scale), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
