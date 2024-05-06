package co.edu.udea.sitas.hateoas;

import co.edu.udea.sitas.controllers.v1.AirplaneModelController;
import co.edu.udea.sitas.domain.dto.AirplaneModelDTO;
import co.edu.udea.sitas.domain.model.AirplaneModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AirplaneModelHateoasAssembler implements RepresentationModelAssembler<AirplaneModel, AirplaneModelDTO> {
    @Override
    @NonNull
    public AirplaneModelDTO toModel(@NonNull AirplaneModel airplaneModel) {
        AirplaneModelDTO airplaneModelDTO = AirplaneModelDTO.buildAirplaneModelDTO(airplaneModel);

        airplaneModelDTO.add(linkTo(
                methodOn(AirplaneModelController.class).getAllAirplaneModels())
                .withRel("airplane-models"));
        airplaneModelDTO.add(linkTo(
                methodOn(AirplaneModelController.class).getAirplaneModelByModel(airplaneModel.getAirplaneModel()))
                .withSelfRel());

        return airplaneModelDTO;
    }

    @Override
    @NonNull
    public CollectionModel<AirplaneModelDTO> toCollectionModel(Iterable<? extends AirplaneModel> entities) {
        List<AirplaneModelDTO> airplaneModels = new ArrayList<>();
        entities.forEach(model -> airplaneModels.add(this.toModel(model)));
        return CollectionModel.of(airplaneModels);
    }
}
