package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.ScaleDTO;
import co.edu.udea.sitas.domain.model.Scale;
import co.edu.udea.sitas.services.ScaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/scales")
public class ScaleController {

    private final ScaleService scaleService;

    @Autowired
    public ScaleController(ScaleService scaleService) {
        this.scaleService = scaleService;
    }

    @GetMapping
    public ResponseEntity<List<ScaleDTO>> getAllScales() {
        List<ScaleDTO> scaleDTOs = scaleService.findAll().stream()
                .map(ScaleDTO::buildScaleDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(scaleDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScaleDTO> getScaleById(@PathVariable("id") Long id) {
        Optional<Scale> scaleOptional = scaleService.findById(id);
        return scaleOptional.map(scale -> new ResponseEntity<>(ScaleDTO.buildScaleDTO(scale), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}