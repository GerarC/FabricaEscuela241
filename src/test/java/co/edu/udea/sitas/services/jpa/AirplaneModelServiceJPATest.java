package co.edu.udea.sitas.services.jpa;

import co.edu.udea.sitas.domain.model.AirplaneModel;
import co.edu.udea.sitas.persistence.AirplaneModelRepository;
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

class AirplaneModelServiceJPATest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private AirplaneModelRepository airplaneModelRepository;

    @InjectMocks
    private AirplaneModelServiceJPA airplaneModelServiceJPA;

    @Test
    void testFindAll() {
        // Arrange
        List<AirplaneModel> airplaneModels = new ArrayList<>();
        when(airplaneModelRepository.findAll()).thenReturn(airplaneModels);

        // Act
        List<AirplaneModel> result = airplaneModelServiceJPA.findAll();

        // Assert
        assertEquals(airplaneModels, result);
    }

    @Test
    void testSave() {
        // Arrange
        AirplaneModel airplaneModel = new AirplaneModel();
        when(airplaneModelRepository.save(airplaneModel)).thenReturn(airplaneModel);

        // Act
        AirplaneModel result = airplaneModelServiceJPA.save(airplaneModel);

        // Assert
        assertEquals(airplaneModel, result);
    }

    @Test
    void testFindById() {
        // Arrange
        String model = "model";
        AirplaneModel airplaneModel = new AirplaneModel();
        when(airplaneModelRepository.findById(model)).thenReturn(Optional.of(airplaneModel));

        // Act
        Optional<AirplaneModel> result = airplaneModelServiceJPA.findById(model);

        // Assert
        assertEquals(Optional.of(airplaneModel), result);
    }

    @Test
    void testUpdate() {
        // Arrange
        String model = "model";
        AirplaneModel airplaneModel = new AirplaneModel();
        when(airplaneModelRepository.findById(model)).thenReturn(Optional.of(airplaneModel));
        when(airplaneModelRepository.save(airplaneModel)).thenReturn(airplaneModel);

        // Act
        Optional<AirplaneModel> result = airplaneModelServiceJPA.update(model, airplaneModel);

        // Assert
        assertEquals(Optional.of(airplaneModel), result);
    }

    @Test
    void testDelete() {
        // Arrange
        String model = "model";
        AirplaneModel airplaneModel = new AirplaneModel();
        when(airplaneModelRepository.findById(model)).thenReturn(Optional.of(airplaneModel));

        // Act
        Optional<AirplaneModel> result = airplaneModelServiceJPA.delete(model);

        // Assert
        assertEquals(Optional.of(airplaneModel), result);
        verify(airplaneModelRepository, times(1)).deleteById(model);
    }

}