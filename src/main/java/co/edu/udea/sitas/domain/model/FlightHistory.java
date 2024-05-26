package co.edu.udea.sitas.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "FlightHistory")
public class FlightHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_history_id")
    private Long flightHistoryId;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @Override
    public String toString() {
        return "FlightHistory{" +
                "flightHistoryId=" + flightHistoryId +
                ", person=" + person +
                ", flight=" + flight +
                '}';
    }
}
