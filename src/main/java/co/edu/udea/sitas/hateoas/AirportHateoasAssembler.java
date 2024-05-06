package co.edu.udea.sitas.hateoas;

import co.edu.udea.sitas.controllers.v1.AirportController;
import co.edu.udea.sitas.domain.dto.AirportDTO;
import co.edu.udea.sitas.domain.model.Airport;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AirportHateoasAssembler implements RepresentationModelAssembler<Airport, AirportDTO> {
    @Override
    @NonNull
    public AirportDTO toModel(@NonNull Airport airplaneModel) {
        AirportDTO airportDTO = AirportDTO.buildAirportDTO(airplaneModel);

        airportDTO.add(linkTo(methodOn(AirportController.class).getAllAirports()).withRel("airports"));
        airportDTO.add(linkTo(
                methodOn(AirportController.class).getAirportByCode(airplaneModel.getAirportCode()))
                .withSelfRel());

        return airportDTO;
    }

    @Override
    @NonNull
    public CollectionModel<AirportDTO> toCollectionModel(Iterable<? extends Airport> entities) {
        List<AirportDTO> airports = new ArrayList<>();
        entities.forEach(airport -> airports.add(this.toModel(airport)));
        return CollectionModel.of(airports);
    }
}
