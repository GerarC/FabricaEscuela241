package co.edu.udea.sitas.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Scale")
public class Scale implements Serializable {
    @Id
    @Column(name = "scale_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long scaleId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "airplane_model", nullable = false)
    private AirplaneModel airplaneModel;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "origin_airport", nullable = false)
    private Airport originAirport;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "destination_airport", nullable = false)
    private Airport destinationAirport;

    @Column(name = "departure_date")
    private LocalDateTime departureDate;

    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;

    @JsonIgnore
    @OneToMany(mappedBy = "scale", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<SearchHistory> searchHistories;

    @Override
    public String toString() {
        return "Scale{" +
                "scale_id=" + scaleId +
                ", flight=" + flight +
                ", airplaneModel=" + airplaneModel +
                ", originAirport=" + originAirport +
                ", destinationAirport=" + destinationAirport +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                '}';
    }
}
