package co.edu.udea.sitas.services.jpa;

import co.edu.udea.sitas.domain.model.Airport;
import co.edu.udea.sitas.persistence.AirportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AirportServiceJPATest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks((this));
    }

    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private AirportServiceJPA airportServiceJPA;

    @Test
    void testFindAll() {
        // Arrange
        List<Airport> airports = new ArrayList<>();
        when(airportRepository.findAll()).thenReturn(airports);

        // Act
        List<Airport> result = airportServiceJPA.findAll();

        // Assert
        assertEquals(airports, result);
    }

    @Test
    void testSave() {
        // Arrange
        Airport airport = new Airport();
        when(airportRepository.save(airport)).thenReturn(airport);

        // Act
        Airport result = airportServiceJPA.save(airport);

        // Assert
        assertEquals(airport, result);
    }

    @Test
    void testFindById() {
        // Arrange
        String airportCode = "ABC";
        Airport airport = new Airport();
        when(airportRepository.findById(airportCode)).thenReturn(Optional.of(airport));

        // Act
        Optional<Airport> result = airportServiceJPA.findById(airportCode);

        // Assert
        assertEquals(Optional.of(airport), result);
    }

    @Test
    void testUpdate() {
        // Arrange
        String airportCode = "ABC";
        Airport airport = new Airport();
        when(airportRepository.findById(airportCode)).thenReturn(Optional.of(airport));
        when(airportRepository.save(airport)).thenReturn(airport);

        // Act
        Optional<Airport> result = airportServiceJPA.update(airportCode, airport);

        // Assert
        assertEquals(Optional.of(airport), result);
    }

    @Test
    void testDelete() {
        // Arrange
        String airportCode = "ABC";
        Airport airport = new Airport();
        when(airportRepository.findById(airportCode)).thenReturn(Optional.of(airport));

        // Act
        Optional<Airport> result = airportServiceJPA.delete(airportCode);

        // Assert
        assertEquals(Optional.of(airport), result);
        verify(airportRepository, times(1)).deleteById(airportCode);
    }
}