package co.edu.udea.sitas.domain.dto;

import co.edu.udea.sitas.domain.model.Airport;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Data
@Setter
@Getter
@ToString
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO representing an airport")
public class AirportDTO {

    @Schema(description = "Airport code", example = "JFK")
    private String airportCode;

    @Schema(description = "Name of the airport", example = "John F. Kennedy International Airport")
    private String name;

    @Schema(description = "Type of the airport", example = "International")
    private String type;

    @Schema(description = "City where the airport is located", example = "New York")
    private String city;

    @Schema(description = "Country where the airport is located", example = "USA")
    private String country;

    @Schema(description = "Number of runways at the airport", example = "4")
    private Integer runways;

    public static AirportDTO buildAirportDTO(Airport airport){
        log.info("Convert airport into AirportDTO");
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

