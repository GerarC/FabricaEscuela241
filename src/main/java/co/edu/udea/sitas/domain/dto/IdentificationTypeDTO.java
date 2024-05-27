package co.edu.udea.sitas.domain.dto;

import co.edu.udea.sitas.domain.model.IdentificationType;
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
public class IdentificationTypeDTO {
    private Long identificationTypeId;
    private String identificationType;

    public static IdentificationTypeDTO buildIdentificationTypeDTO(IdentificationType identificationType) {
        log.info("convert IdentificationType to IdentificationTypeDTO");
        return IdentificationTypeDTO.builder()
                .identificationTypeId(identificationType.getIdentificationTypeId())
                .identificationType(identificationType.getIdentificationType())
                .build();
    }
}
