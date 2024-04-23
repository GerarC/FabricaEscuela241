package co.edu.udea.sitas.services.jpa;

import co.edu.udea.sitas.domain.model.Flight;
import co.edu.udea.sitas.domain.model.Scale;
import co.edu.udea.sitas.persistence.ScaleRepository;
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

class ScaleServiceJPATest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private ScaleRepository scaleRepository;

    @InjectMocks
    private ScaleServiceJPA scaleServiceJPA;

    @Test
    void testFindAll() {
        // Arrange
        List<Scale> scales = new ArrayList<>();
        when(scaleRepository.findAll()).thenReturn(scales);

        // Act
        List<Scale> result = scaleServiceJPA.findAll();

        // Assert
        assertEquals(scales, result);
    }

    @Test
    void testSave() {
        // Arrange
        Scale scale = new Scale();
        when(scaleRepository.save(scale)).thenReturn(scale);

        // Act
        Scale result = scaleServiceJPA.save(scale);

        // Assert
        assertEquals(scale, result);
    }

    @Test
    void testFindById() {
        // Arrange
        Long id = 1L;
        Scale scale = new Scale();
        when(scaleRepository.findById(id)).thenReturn(Optional.of(scale));

        // Act
        Optional<Scale> result = scaleServiceJPA.findById(id);

        // Assert
        assertEquals(Optional.of(scale), result);
    }

    @Test
    void testUpdate() {
        // Arrange
        Long id = 1L;
        Scale scale = new Scale();
        when(scaleRepository.findById(id)).thenReturn(Optional.of(scale));
        when(scaleRepository.save(scale)).thenReturn(scale);

        // Act
        Optional<Scale> result = scaleServiceJPA.update(id, scale);

        // Assert
        assertEquals(Optional.of(scale), result);
    }

    @Test
    void testDelete() {
        // Arrange
        Long id = 1L;
        Scale scale = new Scale();
        when(scaleRepository.findById(id)).thenReturn(Optional.of(scale));

        // Act
        Optional<Scale> result = scaleServiceJPA.delete(id);

        // Assert
        assertEquals(Optional.of(scale), result);
        verify(scaleRepository, times(1)).deleteById(id);
    }

    @Test
    void testFindAllByFlight() {
        // Arrange
        Flight flight = new Flight();
        List<Scale> scales = new ArrayList<>();
        when(scaleRepository.findAllByFlight(flight)).thenReturn(scales);

        // Act
        List<Scale> result = scaleServiceJPA.findAllByFlight(flight);

        // Assert
        assertEquals(scales, result);
    }
}