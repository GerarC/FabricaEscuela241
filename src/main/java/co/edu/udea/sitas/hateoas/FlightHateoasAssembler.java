package co.edu.udea.sitas.hateoas;

import co.edu.udea.sitas.controllers.v1.FlightController;
import co.edu.udea.sitas.domain.dto.FlightDTO;
import co.edu.udea.sitas.domain.model.Flight;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FlightHateoasAssembler implements RepresentationModelAssembler<Flight, FlightDTO> {
    @Override
    @NonNull
    public FlightDTO toModel(@NonNull Flight flight) {
        FlightDTO flightDTO = FlightDTO.buildFlightDTO(flight);

        flightDTO.add(linkTo(methodOn(FlightController.class).findALL(null)).withRel("flights"));
        flightDTO.add(linkTo(methodOn(FlightController.class).findScalesById(flight.getFlightId())).withRel("scales"));
        flightDTO.add(linkTo(methodOn(FlightController.class).findById(flight.getFlightId())).withSelfRel());

        return flightDTO;
    }

    @Override
    @NonNull
    public CollectionModel<FlightDTO> toCollectionModel(Iterable<? extends Flight> entities) {
        List<FlightDTO> flights = new ArrayList<>();
        entities.forEach(flight -> flights.add(this.toModel(flight)));
        return CollectionModel.of(flights);
    }
}
