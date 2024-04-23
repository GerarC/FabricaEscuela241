package co.edu.udea.sitas.domain.dto;

import co.edu.udea.sitas.domain.model.AirplaneModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AirplaneModelDTOTest {
    @BeforeEach
    void setUp() {

    }

    @Test
    void buildAirplaneModelDTO() {
        // Arrange
        AirplaneModel airplaneModel = new AirplaneModel();
        airplaneModel.setAirplaneModel("XYZ123");
        airplaneModel.setFamily("Family A");
        airplaneModel.setCapacity(200);
        airplaneModel.setCargoCapacity(1000.0f);
        airplaneModel.setVolumeCapacity(2000.0f);

        // Act
        AirplaneModelDTO dto = AirplaneModelDTO.buildAirplaneModelDTO(airplaneModel);

        // Assert
        assertEquals("XYZ123", dto.getAirplaneModel());
        assertEquals("Family A", dto.getFamily());
        assertEquals(200, dto.getCapacity());
        assertEquals(1000.0f, dto.getCargoCapacity());
        assertEquals(2000.0f, dto.getVolumeCapacity());
    }
}