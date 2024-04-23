package co.edu.udea.sitas.domain.dto;

import co.edu.udea.sitas.domain.model.AirplaneModel;
import co.edu.udea.sitas.domain.model.Airport;
import co.edu.udea.sitas.domain.model.Flight;
import co.edu.udea.sitas.domain.model.Scale;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScaleDTOTest {

    @Test
    void buildScaleDTO() {
        // Arrange
        Scale scale = new Scale();
        scale.setScaleId(1L);

        Flight flight = mock(Flight.class);
        when(flight.getFlightId()).thenReturn(100L);

        AirplaneModel airplaneModel = mock(AirplaneModel.class);
        when(airplaneModel.getAirplaneModel()).thenReturn("Boeing 747");

        Airport originAirport = mock(Airport.class);
        when(originAirport.getAirportCode()).thenReturn("A1");

        Airport destinationAirport = mock(Airport.class);
        when(destinationAirport.getAirportCode()).thenReturn("B1");

        scale.setFlight(flight);
        scale.setAirplaneModel(airplaneModel);
        scale.setOriginAirport(originAirport);
        scale.setDestinationAirport(destinationAirport);
        scale.setDepartureDate(LocalDateTime.now());
        scale.setArrivalDate(LocalDateTime.now().plusHours(2));

        // Act
        ScaleDTO scaleDTO = ScaleDTO.buildScaleDTO(scale);

        // Assert
        assertEquals(1L, scaleDTO.getScaleId());
        assertEquals(100L, scaleDTO.getFlightId());
        assertEquals("Boeing 747", scaleDTO.getAirplaneModel());
        assertEquals("A1", scaleDTO.getOriginAirport());
        assertEquals("B1", scaleDTO.getDestinationAirport());
        // You can add assertions for departureDate and arrivalDate if needed
    }
}