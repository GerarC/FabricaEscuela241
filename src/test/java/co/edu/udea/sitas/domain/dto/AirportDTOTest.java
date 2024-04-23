package co.edu.udea.sitas.domain.dto;

import co.edu.udea.sitas.domain.model.Airport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AirportDTOTest {


    @Test
    void buildAirportDTO() {
        // Arrange
        Airport airport = new Airport();
        airport.setAirportCode("ABC");
        airport.setName("Airport A");
        airport.setType("International");
        airport.setCity("City A");
        airport.setCountry("Country A");
        airport.setRunways(2);

        // Act
        AirportDTO dto = AirportDTO.buildAirportDTO(airport);

        // Assert
        assertEquals("ABC", dto.getAirportCode());
        assertEquals("Airport A", dto.getName());
        assertEquals("International", dto.getType());
        assertEquals("City A", dto.getCity());
        assertEquals("Country A", dto.getCountry());
        assertEquals(2, dto.getRunways());
    }
}