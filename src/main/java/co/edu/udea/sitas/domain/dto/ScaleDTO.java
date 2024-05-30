package co.edu.udea.sitas.domain.dto;

import co.edu.udea.sitas.domain.model.Scale;
import lombok.*;
import lombok.extern.slf4j.Slf4j;


import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Setter
@Getter
@ToString
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO representing a scale")
public class ScaleDTO {

    @Schema(description = "Scale ID", example = "1")
    private Long scaleId;

    @Schema(description = "Flight ID", example = "100")
    private Long flightId;

    @Schema(description = "Airplane model", example = "Boeing 747")
    private String airplaneModel;

    @Schema(description = "Origin airport code", example = "JFK")
    private String originAirport;

    @Schema(description = "Destination airport code", example = "LAX")
    private String destinationAirport;

    @Schema(description = "Departure date and time", example = "2024-06-01T10:15:30")
    private String departureDate;

    @Schema(description = "Arrival date and time", example = "2024-06-01T14:30:00")
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

