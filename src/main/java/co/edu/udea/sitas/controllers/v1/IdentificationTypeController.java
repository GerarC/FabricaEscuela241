package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.IdentificationTypeDTO;
import co.edu.udea.sitas.domain.model.IdentificationType;
import co.edu.udea.sitas.services.IdentificationTypeService;
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
@RequestMapping("/v1/identification-types")
@Tag(name = "Identification Type", description = "The Identification Type API")
public class IdentificationTypeController {

    private final IdentificationTypeService identificationTypeService;

    @Autowired
    public IdentificationTypeController(IdentificationTypeService identificationTypeService) {
        this.identificationTypeService = identificationTypeService;
    }

    @Operation(summary = "Get all identification types", description = "Returns a list of all identification types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the identification types", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = IdentificationTypeDTO.class))
            })
    })
    @GetMapping
    public ResponseEntity<List<IdentificationTypeDTO>> getAllIdentificationTypes() {
        List<IdentificationTypeDTO> identificationTypeDTOs = identificationTypeService.findAll().stream()
                .map(IdentificationTypeDTO::buildIdentificationTypeDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(identificationTypeDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Get identification type by ID", description = "Returns a single identification type by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the identification type", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = IdentificationTypeDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Identification type not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<IdentificationTypeDTO> getIdentificationTypeById(@Parameter(description = "ID of the identification type") @PathVariable Long id) {
        Optional<IdentificationType> identificationTypeOptional = identificationTypeService.findById(id);
        return identificationTypeOptional.map(identificationType -> new ResponseEntity<>(IdentificationTypeDTO.buildIdentificationTypeDTO(identificationType), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Create a new identification type", description = "Creates a new identification type.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Identification type created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = IdentificationTypeDTO.class))
            })
    })
    @PostMapping
    public ResponseEntity<IdentificationTypeDTO> createIdentificationType(@RequestBody IdentificationTypeDTO identificationTypeDTO) {
        IdentificationType identificationType = IdentificationType.builder()
                .identificationType(identificationTypeDTO.getIdentificationType())
                .build();
        IdentificationType savedIdentificationType = identificationTypeService.save(identificationType);
        return new ResponseEntity<>(IdentificationTypeDTO.buildIdentificationTypeDTO(savedIdentificationType), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing identification type", description = "Updates an existing identification type by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Identification type updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = IdentificationTypeDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Identification type not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<IdentificationTypeDTO> updateIdentificationType(
            @Parameter(description = "ID of the identification type") @PathVariable Long id,
            @RequestBody IdentificationTypeDTO identificationTypeDTO) {
        Optional<IdentificationType> identificationTypeOptional = identificationTypeService.update(id, IdentificationType.builder()
                .identificationType(identificationTypeDTO.getIdentificationType())
                .build());
        return identificationTypeOptional.map(identificationType -> new ResponseEntity<>(IdentificationTypeDTO.buildIdentificationTypeDTO(identificationType), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}


