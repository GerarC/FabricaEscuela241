package co.edu.udea.sitas.domain.dto;

import co.edu.udea.sitas.domain.model.FlightHistory;
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
@Schema(description = "DTO representing a flight history")
public class FlightHistoryDTO {

    @Schema(description = "Flight history ID", example = "1")
    private Long flightHistoryId;

    @Schema(description = "Person ID", example = "100")
    private Long personId;

    @Schema(description = "Flight ID", example = "200")
    private Long flightId;

    public static FlightHistoryDTO buildFlightHistoryDTO(FlightHistory flightHistory) {
        log.info("Convert FlightHistory to FlightHistoryDTO");
        return FlightHistoryDTO.builder()
                .flightHistoryId(flightHistory.getFlightHistoryId())
                .personId(flightHistory.getPerson().getPersonId())
                .flightId(flightHistory.getFlight().getFlightId())
                .build();
    }
}


