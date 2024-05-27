package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.IdentificationTypeDTO;
import co.edu.udea.sitas.domain.model.IdentificationType;
import co.edu.udea.sitas.services.IdentificationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/identification-types")
public class IdentificationTypeController {

    private final IdentificationTypeService identificationTypeService;

    @Autowired
    public IdentificationTypeController(IdentificationTypeService identificationTypeService) {
        this.identificationTypeService = identificationTypeService;
    }

    @GetMapping
    public ResponseEntity<List<IdentificationTypeDTO>> getAllIdentificationTypes() {
        List<IdentificationTypeDTO> identificationTypeDTOs = identificationTypeService.findAll().stream()
                .map(IdentificationTypeDTO::buildIdentificationTypeDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(identificationTypeDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IdentificationTypeDTO> getIdentificationTypeById(@PathVariable Long id) {
        Optional<IdentificationType> identificationTypeOptional = identificationTypeService.findById(id);
        return identificationTypeOptional.map(identificationType -> new ResponseEntity<>(IdentificationTypeDTO.buildIdentificationTypeDTO(identificationType), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<IdentificationTypeDTO> createIdentificationType(@RequestBody IdentificationTypeDTO identificationTypeDTO) {
        IdentificationType identificationType = IdentificationType.builder()
                .identificationType(identificationTypeDTO.getIdentificationType())
                .build();
        IdentificationType savedIdentificationType = identificationTypeService.save(identificationType);
        return new ResponseEntity<>(IdentificationTypeDTO.buildIdentificationTypeDTO(savedIdentificationType), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IdentificationTypeDTO> updateIdentificationType(@PathVariable Long id, @RequestBody IdentificationTypeDTO identificationTypeDTO) {
        Optional<IdentificationType> identificationTypeOptional = identificationTypeService.update(id, IdentificationType.builder()
                .identificationType(identificationTypeDTO.getIdentificationType())
                .build());
        return identificationTypeOptional.map(identificationType -> new ResponseEntity<>(IdentificationTypeDTO.buildIdentificationTypeDTO(identificationType), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

