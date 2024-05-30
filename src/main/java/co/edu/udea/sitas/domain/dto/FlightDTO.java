package co.edu.udea.sitas.domain.dto;

import co.edu.udea.sitas.domain.model.Airport;
import co.edu.udea.sitas.domain.model.Flight;
import co.edu.udea.sitas.domain.model.Scale;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;


@Data
@Setter
@Getter
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO representing a flight")
public class FlightDTO {
    @Schema(description = "Flight ID", example = "1")
    private Long flightId;

    @Schema(description = "Flight number", example = "AV123")
    private String flightNumber;

    @Schema(description = "Base price of the flight", example = "200.0")
    private Float basePrice;

    @Schema(description = "Tax percentage", example = "10.0")
    private Float taxPercent;

    @Schema(description = "Surcharge", example = "20.0")
    private Float surcharge;

    @Schema(description = "Number of scales", example = "2")
    private Integer scaleNumber;

    @Schema(description = "Origin airport")
    private Airport originAirport;

    @Schema(description = "Destination airport")
    private Airport destinationAirport;

    @Schema(description = "Departure date and time", example = "2024-06-01T10:15:30")
    private LocalDateTime departureDate;

    @Schema(description = "Arrival date and time", example = "2024-06-01T14:30:00")
    private LocalDateTime arrivalDate;

    @Schema(description = "List of scales")
    private List<Scale> scales;

    @Override
    public String toString() {
        return "FlightDTO{" +
                "arrivalDate=" + arrivalDate +
                ", departureDate=" + departureDate +
                ", destinationAirport=" + destinationAirport +
                ", originAirport=" + originAirport +
                ", scaleNumber=" + scaleNumber +
                ", surcharge=" + surcharge +
                ", taxPercent=" + taxPercent +
                ", basePrice=" + basePrice +
                ", flightNumber='" + flightNumber + '\'' +
                ", flightId=" + flightId +
                '}';
    }

    public static FlightDTO buildFlightDTO(Flight flight){
        log.info("Convert flight into FlightDTO");
        int scaleNumber = flight.getScales().size();
        Scale initialScale = flight.getScales().get(0);
        Scale finalScale = flight.getScales().get(scaleNumber - 1);

        return FlightDTO.builder()
                .flightId(flight.getFlightId())
                .flightNumber(flight.getFlightNumber())
                .basePrice(flight.getBasePrice())
                .taxPercent(flight.getTaxPercent())
                .surcharge(flight.getSurcharge())
                .scaleNumber(scaleNumber)
                .originAirport(initialScale.getOriginAirport())
                .destinationAirport(finalScale.getDestinationAirport())
                .departureDate(initialScale.getDepartureDate())
                .arrivalDate(finalScale.getArrivalDate())
                .build();
    }
}

