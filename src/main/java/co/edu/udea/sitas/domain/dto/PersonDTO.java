package co.edu.udea.sitas.domain.dto;

import co.edu.udea.sitas.domain.model.Person;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Data
@Setter
@Getter
@ToString
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDTO {
    private Long personId;
    private Long identificationTypeId;
    private String identificationNumber;
    private String firstName;
    private String lastName;
    private String genre;
    private LocalDate birthDate;
    private String phoneNumber;
    private String country;
    private String province;
    private String city;
    private String residence;
    private String email;
    private String accessKey;

    public static PersonDTO buildPersonDTO(Person person) {
        log.info("convert Person to PersonDTO");
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

