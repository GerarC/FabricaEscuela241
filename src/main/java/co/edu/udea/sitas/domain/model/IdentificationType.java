package co.edu.udea.sitas.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "IdentificationType")
public class IdentificationType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "identification_type_id")
    private Long identificationTypeId;

    @Column(name = "identification_type")
    private String identificationType;

    @JsonIgnore
    @OneToMany(mappedBy = "identificationType", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Person> persons;

    @Override
    public String toString() {
        return "IdentificationType{" +
                "identificationTypeId=" + identificationTypeId +
                ", identificationType='" + identificationType + '\'' +
                '}';
    }
}
