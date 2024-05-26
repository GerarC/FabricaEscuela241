package co.edu.udea.sitas.domain.dto;

import co.edu.udea.sitas.domain.model.FlightHistory;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@ToString
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightHistoryDTO {
    private Long flightHistoryId;
    private Long personId;
    private Long flightId;


    public static FlightHistoryDTO buildFlightHistoryDTO(FlightHistory flightHistory) {
        log.info("convert FlightHistory to FlightHistoryDTO");
        return FlightHistoryDTO.builder()
                .flightHistoryId(flightHistory.getFlightHistoryId())
                .personId(flightHistory.getPerson().getPersonId())
                .flightId(flightHistory.getFlight().getFlightId())
                .build();
    }
}

