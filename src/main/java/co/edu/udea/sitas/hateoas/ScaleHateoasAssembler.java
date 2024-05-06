package co.edu.udea.sitas.hateoas;

import co.edu.udea.sitas.controllers.v1.ScaleController;
import co.edu.udea.sitas.domain.dto.ScaleDTO;
import co.edu.udea.sitas.domain.model.Scale;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ScaleHateoasAssembler implements RepresentationModelAssembler<Scale, ScaleDTO> {
    @Override
    @NonNull
    public ScaleDTO toModel(@NonNull Scale scale) {
        ScaleDTO scaleDTO = ScaleDTO.buildScaleDTO(scale);

        scaleDTO.add(linkTo(methodOn(ScaleController.class).getAllScales()).withRel("scales"));
        scaleDTO.add(linkTo(methodOn(ScaleController.class).getScaleById(scale.getScaleId())).withSelfRel());

        return scaleDTO;
    }

    @Override
    @NonNull
    public CollectionModel<ScaleDTO> toCollectionModel(Iterable<? extends Scale> entities) {
        List<ScaleDTO> scales = new ArrayList<>();
        entities.forEach(scale -> scales.add(this.toModel(scale)));
        return CollectionModel.of(scales);
    }
}
