package co.edu.udea.sitas.domain.dto;

import co.edu.udea.sitas.domain.model.Scale;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.RepresentationModel;


@Setter
@Getter
@ToString
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScaleDTO extends RepresentationModel<ScaleDTO> {
    private Long scaleId;
    private Long flightId;
    private String airplaneModel;
    private String originAirport;
    private String destinationAirport;
    private String departureDate;
    private String arrivalDate;

    public static ScaleDTO buildScaleDTO(Scale scale) {
        log.info("Build ScaleDTO");
        return ScaleDTO.builder()
                .scaleId(scale.getScaleId())
                .flightId(scale.getFlight().getFlightId())
                .airplaneModel(scale.getAirplaneModel().getAirplaneModel())
                .originAirport(scale.getOriginAirport().getAirportCode())
                .destinationAirport(scale.getDestinationAirport().getAirportCode())
                .departureDate(scale.getDepartureDate().toString())
                .arrivalDate(scale.getArrivalDate().toString())
                .build();
    }
}
