package co.edu.udea.sitas.domain.dto;

import co.edu.udea.sitas.domain.model.Airport;
import co.edu.udea.sitas.domain.model.Flight;
import co.edu.udea.sitas.domain.model.Scale;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightDTO extends RepresentationModel<FlightDTO> {
    // Attributes
    private Long flightId;
    private String flightNumber;
    private Float basePrice;
    private Float taxPercent;
    private Float surcharge;
    private Integer scaleNumber;
    private Airport originAirport;
    private Airport destinationAirport;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private List<Scale> scales;

    @Override
    @NonNull
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
        log.info("convert flight in a FlightDTO");
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
