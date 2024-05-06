package co.edu.udea.sitas.domain.dto;

import co.edu.udea.sitas.domain.model.Airport;
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
public class AirportDTO extends RepresentationModel<AirportDTO> {
    private String airportCode;
    private String name;
    private String type;
    private String city;
    private String country;
    private Integer runways;

    public static AirportDTO buildAirportDTO(Airport airport){
        log.info("convert airport in a AirportDTO");
        return AirportDTO.builder()
                .airportCode(airport.getAirportCode())
                .name(airport.getName())
                .type(airport.getType())
                .city(airport.getCity())
                .country(airport.getCountry())
                .runways(airport.getRunways())
                .build();
    }
}
