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
@Table(name = "AirplaneModel")
public class AirplaneModel implements Serializable {
    @Id
    @Column(name = "airplane_model")
    private String airplaneModel;

    @Column(name = "family")
    private String family;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "cargo_capacity")
    private Float cargoCapacity;

    @Column(name = "volume_capacity")
    private Float volumeCapacity;

    @JsonIgnore
    @OneToMany(mappedBy = "airplaneModel", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Scale> scales;

    @Override
    public String toString() {
        return "AirplaneModel{" +
                "volumeCapacity=" + volumeCapacity +
                ", cargoCapacity=" + cargoCapacity +
                ", capacity=" + capacity +
                ", family='" + family + '\'' +
                ", airplaneModel='" + airplaneModel + '\'' +
                '}';
    }
}
