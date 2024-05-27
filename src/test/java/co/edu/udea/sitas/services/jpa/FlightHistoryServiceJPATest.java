package co.edu.udea.sitas.services.jpa;

import co.edu.udea.sitas.domain.model.FlightHistory;
import co.edu.udea.sitas.persistence.FlightHistoryRepository;
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

class FlightHistoryServiceJPATest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private FlightHistoryRepository flightHistoryRepository;

    @InjectMocks
    private FlightHistoryServiceJPA flightHistoryServiceJPA;

    @Test
    void testFindAll() {
        // Arrange
        List<FlightHistory> flightHistories = new ArrayList<>();
        when(flightHistoryRepository.findAll()).thenReturn(flightHistories);

        // Act
        List<FlightHistory> result = flightHistoryServiceJPA.findAll();

        // Assert
        assertEquals(flightHistories, result);
    }

    @Test
    void testSave() {
        // Arrange
        FlightHistory flightHistory = new FlightHistory();
        when(flightHistoryRepository.save(flightHistory)).thenReturn(flightHistory);

        // Act
        FlightHistory result = flightHistoryServiceJPA.save(flightHistory);

        // Assert
        assertEquals(flightHistory, result);
    }

    @Test
    void testFindById() {
        // Arrange
        Long id = 1L;
        FlightHistory flightHistory = new FlightHistory();
        when(flightHistoryRepository.findById(id)).thenReturn(Optional.of(flightHistory));

        // Act
        Optional<FlightHistory> result = flightHistoryServiceJPA.findById(id);

        // Assert
        assertEquals(Optional.of(flightHistory), result);
    }

    @Test
    void testUpdate() {
        // Arrange
        Long id = 1L;
        FlightHistory flightHistory = new FlightHistory();
        when(flightHistoryRepository.findById(id)).thenReturn(Optional.of(flightHistory));
        when(flightHistoryRepository.save(flightHistory)).thenReturn(flightHistory);

        // Act
        Optional<FlightHistory> result = flightHistoryServiceJPA.update(id, flightHistory);

        // Assert
        assertEquals(Optional.of(flightHistory), result);
    }

    @Test
    void testDelete() {
        // Arrange
        Long id = 1L;
        FlightHistory flightHistory = new FlightHistory();
        when(flightHistoryRepository.findById(id)).thenReturn(Optional.of(flightHistory));

        // Act
        Optional<FlightHistory> result = flightHistoryServiceJPA.delete(id);

        // Assert
        assertEquals(Optional.of(flightHistory), result);
        verify(flightHistoryRepository, times(1)).deleteById(id);
    }
}

