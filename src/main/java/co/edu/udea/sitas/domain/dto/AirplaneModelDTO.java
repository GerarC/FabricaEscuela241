package co.edu.udea.sitas.domain.dto;

import co.edu.udea.sitas.domain.model.AirplaneModel;
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
@Schema(description = "DTO representing an airplane model")
public class AirplaneModelDTO {

    @Schema(description = "Airplane model", example = "Boeing 747")
    private String airplaneModel;

    @Schema(description = "Airplane family", example = "Jumbo Jet")
    private String family;

    @Schema(description = "Airplane capacity", example = "416")
    private Integer capacity;

    @Schema(description = "Airplane cargo capacity", example = "140.0")
    private Float cargoCapacity;

    @Schema(description = "Airplane volume capacity", example = "150.0")
    private Float volumeCapacity;

    public static AirplaneModelDTO buildAirplaneModelDTO(AirplaneModel airplaneModel){
        log.info("Convert airplane model into AirplaneModelDTO");
        return AirplaneModelDTO.builder()
                .airplaneModel(airplaneModel.getAirplaneModel())
                .family(airplaneModel.getFamily())
                .capacity(airplaneModel.getCapacity())
                .cargoCapacity(airplaneModel.getCargoCapacity())
                .volumeCapacity(airplaneModel.getVolumeCapacity())
                .build();
    }
}

