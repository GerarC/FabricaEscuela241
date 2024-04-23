package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.AirplaneModelDTO;
import co.edu.udea.sitas.domain.model.AirplaneModel;
import co.edu.udea.sitas.services.AirplaneModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AirplaneModelControllerTest {

    @Mock
    private AirplaneModelService airplaneModelService;

    @InjectMocks
    private AirplaneModelController airplaneModelController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        airplaneModelController = new AirplaneModelController(airplaneModelService);
    }

    @Test
    void getAllAirplaneModels() {
        // Arrange
        AirplaneModelDTO airplaneModelDTO1 = new AirplaneModelDTO();
        AirplaneModelDTO airplaneModelDTO2 = new AirplaneModelDTO();
        List<AirplaneModelDTO> expectedModels = Arrays.asList(airplaneModelDTO1, airplaneModelDTO2);

        when(airplaneModelService.findAll()).thenReturn(Arrays.asList(new AirplaneModel(), new AirplaneModel()));

        // Act
        ResponseEntity<List<AirplaneModelDTO>> response = airplaneModelController.getAllAirplaneModels();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedModels, response.getBody());
        verify(airplaneModelService, times(1)).findAll();
    }

    @Test
    void getAirplaneModelByModel() {
        // Arrange
        String model = "model";
        AirplaneModelDTO expectedModel = new AirplaneModelDTO();
        when(airplaneModelService.findById(model)).thenReturn(Optional.of(new AirplaneModel()));

        // Act
        ResponseEntity<AirplaneModelDTO> response = airplaneModelController.getAirplaneModelByModel(model);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedModel, response.getBody());
        verify(airplaneModelService, times(1)).findById(model);
    }

    @Test
    void createAirplaneModel() {
        // Arrange
        AirplaneModelDTO requestDto = new AirplaneModelDTO();
        requestDto.setAirplaneModel("model");
        requestDto.setFamily("family");
        requestDto.setCapacity(200);
        requestDto.setCargoCapacity(1000.0f);
        requestDto.setVolumeCapacity(2000.0f);

        AirplaneModel savedModel = new AirplaneModel();
        when(airplaneModelService.save(any())).thenReturn(savedModel);

        // Act
        ResponseEntity<AirplaneModelDTO> response = airplaneModelController.createAirplaneModel(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(airplaneModelService, times(1)).save(any());
    }

    @Test
    void updateAirplaneModel() {
        // Arrange
        String model = "model";
        AirplaneModelDTO requestDto = new AirplaneModelDTO();
        requestDto.setAirplaneModel("updatedModel");
        requestDto.setFamily("updatedFamily");
        requestDto.setCapacity(300);
        requestDto.setCargoCapacity(1500.0f);
        requestDto.setVolumeCapacity(3000.0f);

        AirplaneModel updatedModel = new AirplaneModel();
        when(airplaneModelService.update(eq(model), any())).thenReturn(Optional.of(updatedModel));

        // Act
        ResponseEntity<AirplaneModelDTO> response = airplaneModelController.updateAirplaneModel(model, requestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(airplaneModelService, times(1)).update(eq(model), any());
    }
}