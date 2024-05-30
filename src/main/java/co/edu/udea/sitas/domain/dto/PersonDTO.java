package co.edu.udea.sitas.domain.dto;

import co.edu.udea.sitas.domain.model.Person;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;


@Data
@Setter
@Getter
@ToString
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO representing a person")
public class PersonDTO {

    @Schema(description = "Person ID", example = "1")
    private Long personId;

    @Schema(description = "Identification type ID", example = "2")
    private Long identificationTypeId;

    @Schema(description = "Identification number", example = "123456789")
    private String identificationNumber;

    @Schema(description = "First name", example = "John")
    private String firstName;

    @Schema(description = "Last name", example = "Doe")
    private String lastName;

    @Schema(description = "Gender", example = "Male")
    private String genre;

    @Schema(description = "Birth date", example = "1990-01-01")
    private LocalDate birthDate;

    @Schema(description = "Phone number", example = "123-456-7890")
    private String phoneNumber;

    @Schema(description = "Country", example = "USA")
    private String country;

    @Schema(description = "Province", example = "California")
    private String province;

    @Schema(description = "City", example = "Los Angeles")
    private String city;

    @Schema(description = "Residence", example = "123 Main St")
    private String residence;

    @Schema(description = "Email", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Access key", example = "abc123")
    private String accessKey;

    public static PersonDTO buildPersonDTO(Person person) {
        log.info("Convert Person to PersonDTO");
        return PersonDTO.builder()
                .personId(person.getPersonId())
                .identificationTypeId(person.getIdentificationType().getIdentificationTypeId())
                .identificationNumber(person.getIdentificationNumber())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .genre(person.getGenre())
                .birthDate(person.getBirthDate())
                .phoneNumber(person.getPhoneNumber())
                .country(person.getCountry())
                .province(person.getProvince())
                .city(person.getCity())
                .residence(person.getResidence())
                .email(person.getEmail())
                .accessKey(person.getAccessKey())
                .build();
    }
}


