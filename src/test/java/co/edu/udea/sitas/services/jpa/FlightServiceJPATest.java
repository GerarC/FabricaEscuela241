package co.edu.udea.sitas.services.jpa;

import co.edu.udea.sitas.domain.model.Flight;
import co.edu.udea.sitas.persistence.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceJPATest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightServiceJPA flightServiceJPA;

    @Test
    void testSave() {
        // Arrange
        Flight flight = new Flight();
        when(flightRepository.save(flight)).thenReturn(flight);

        // Act
        Flight result = flightServiceJPA.save(flight);

        // Assert
        assertEquals(flight, result);
    }

    @Test
    void testFindAll_WithParameters() {
        // Arrange
        Map<String, String> parameters = new HashMap<>();
        parameters.put("start-date", LocalDateTime.now().toString());
        parameters.put("end-date", LocalDateTime.now().plusDays(1).toString());
        // Add more parameters as needed...

        List<Flight> flights = List.of(new Flight(), new Flight());
        when(flightRepository.findAll(ArgumentMatchers.<Specification<Flight>>any())).thenReturn(flights);

        // Act
        List<Flight> result = flightServiceJPA.findAll(parameters);

        // Assert
        assertEquals(flights, result);
    }

    @Test
    void testFindById() {
        // Arrange
        Long id = 1L;
        Flight flight = new Flight();
        when(flightRepository.findById(id)).thenReturn(Optional.of(flight));

        // Act
        Optional<Flight> result = flightServiceJPA.findById(id);

        // Assert
        assertEquals(Optional.of(flight), result);
    }

    @Test
    void testUpdate() {
        // Arrange
        Long id = 1L;
        Flight flight = new Flight();
        when(flightRepository.findById(id)).thenReturn(Optional.of(flight));
        when(flightRepository.save(flight)).thenReturn(flight);

        // Act
        Optional<Flight> result = flightServiceJPA.update(id, flight);

        // Assert
        assertEquals(Optional.of(flight), result);
    }

    @Test
    void testDelete() {
        // Arrange
        Long id = 1L;
        Flight flight = new Flight();
        when(flightRepository.findById(id)).thenReturn(Optional.of(flight));

        // Act
        Optional<Flight> result = flightServiceJPA.delete(id);

        // Assert
        assertEquals(Optional.of(flight), result);
        verify(flightRepository, times(1)).deleteById(id);
    }
}