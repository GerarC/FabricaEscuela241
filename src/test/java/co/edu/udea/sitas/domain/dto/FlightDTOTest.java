package co.edu.udea.sitas.domain.dto;

import co.edu.udea.sitas.domain.model.Airport;
import co.edu.udea.sitas.domain.model.Flight;
import co.edu.udea.sitas.domain.model.Scale;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightDTOTest {

    @Test
    void buildFlightDTO() {
        // Arrange
        Flight flight = new Flight();
        flight.setFlightId(1L);
        flight.setFlightNumber("ABC123");
        flight.setBasePrice(100.0F);
        flight.setTaxPercent(5.0F);
        flight.setSurcharge(10.0F);

        Scale initialScale = new Scale();
        Airport airportA = Airport.builder().name("Airport A").airportCode("AIA").build();
        initialScale.setOriginAirport(airportA);
        initialScale.setDepartureDate(LocalDateTime.now());

        Scale finalScale = new Scale();
        Airport airportB = Airport.builder().name("Airport B").airportCode("AIB").build();
        finalScale.setDestinationAirport(airportB);
        finalScale.setArrivalDate(LocalDateTime.now().plusHours(2));

        List<Scale> scales = new ArrayList<>();
        scales.add(initialScale);
        scales.add(finalScale);
        flight.setScales(scales);

        // Act
        FlightDTO dto = FlightDTO.buildFlightDTO(flight);

        // Assert
        assertEquals(1L, dto.getFlightId());
        assertEquals("ABC123", dto.getFlightNumber());
        assertEquals(100.0F, dto.getBasePrice());
        assertEquals(5.0F, dto.getTaxPercent());
        assertEquals(10.0F, dto.getSurcharge());
        assertEquals(2, dto.getScaleNumber());
        // You can add more assertions for departureDate and arrivalDate if needed
    }
}