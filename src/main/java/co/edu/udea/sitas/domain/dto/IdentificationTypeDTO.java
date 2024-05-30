package co.edu.udea.sitas.domain.dto;

import co.edu.udea.sitas.domain.model.IdentificationType;
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
@Schema(description = "DTO representing an identification type")
public class IdentificationTypeDTO {

    @Schema(description = "Identification type ID", example = "1")
    private Long identificationTypeId;

    @Schema(description = "Type of identification", example = "Passport")
    private String identificationType;

    public static IdentificationTypeDTO buildIdentificationTypeDTO(IdentificationType identificationType) {
        log.info("Convert IdentificationType to IdentificationTypeDTO");
        return IdentificationTypeDTO.builder()
                .identificationTypeId(identificationType.getIdentificationTypeId())
                .identificationType(identificationType.getIdentificationType())
                .build();
    }
}

