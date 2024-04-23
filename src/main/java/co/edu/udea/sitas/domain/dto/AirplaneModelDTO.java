package co.edu.udea.sitas.domain.dto;

import co.edu.udea.sitas.domain.model.AirplaneModel;
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
public class AirplaneModelDTO {
    private String airplaneModel;
    private String family;
    private Integer capacity;
    private Float cargoCapacity;
    private Float volumeCapacity;

    public static AirplaneModelDTO buildAirplaneModelDTO(AirplaneModel airplaneModel){
        log.info("convert airplane model in a AirplaneModelDTO");
        return AirplaneModelDTO.builder()
                .airplaneModel(airplaneModel.getAirplaneModel())
                .family(airplaneModel.getFamily())
                .capacity(airplaneModel.getCapacity())
                .cargoCapacity(airplaneModel.getCargoCapacity())
                .volumeCapacity(airplaneModel.getVolumeCapacity())
                .build();
    }
}
