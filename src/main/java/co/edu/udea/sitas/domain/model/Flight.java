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
@Table(name = "Flight")
public class Flight implements Serializable {
    @Id
    @Column(name = "flight_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long flightId;

    @Column(name = "flight_number")
    private String flightNumber;

    @Column(name = "base_price")
    private Float basePrice;

    @Column(name = "tax_percent")
    private Float taxPercent;

    @Column(name = "surcharge")
    private Float surcharge;

    @JsonIgnore
    @OneToMany(mappedBy = "flight", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Scale> scales;

    @JsonIgnore
    @OneToMany(mappedBy = "flight", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<FlightHistory> histories;

    @Override
    public String toString() {
        return "Flight{" +
                "flightId=" + flightId +
                ", flightNumber='" + flightNumber + '\'' +
                ", basePrice=" + basePrice +
                ", taxPercent=" + taxPercent +
                ", surcharge=" + surcharge +
                '}';
    }
}
