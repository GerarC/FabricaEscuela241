package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.IdentificationTypeDTO;
import co.edu.udea.sitas.domain.model.IdentificationType;
import co.edu.udea.sitas.services.IdentificationTypeService;
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

class IdentificationTypeControllerTest {

    @Mock
    private IdentificationTypeService identificationTypeService;

    @InjectMocks
    private IdentificationTypeController identificationTypeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllIdentificationTypes() {
        // Arrange
        IdentificationTypeDTO identificationTypeDTO1 = new IdentificationTypeDTO();
        IdentificationTypeDTO identificationTypeDTO2 = new IdentificationTypeDTO();
        List<IdentificationTypeDTO> expectedIdentificationTypes = Arrays.asList(identificationTypeDTO1, identificationTypeDTO2);

        when(identificationTypeService.findAll()).thenReturn(Arrays.asList(new IdentificationType(), new IdentificationType()));

        // Act
        ResponseEntity<List<IdentificationTypeDTO>> response = identificationTypeController.getAllIdentificationTypes();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedIdentificationTypes, response.getBody());
        verify(identificationTypeService, times(1)).findAll();
    }

    @Test
    void getIdentificationTypeById() {
        // Arrange
        Long id = 1L;
        IdentificationTypeDTO expectedIdentificationType = new IdentificationTypeDTO();
        when(identificationTypeService.findById(id)).thenReturn(Optional.of(new IdentificationType()));

        // Act
        ResponseEntity<IdentificationTypeDTO> response = identificationTypeController.getIdentificationTypeById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedIdentificationType, response.getBody());
        verify(identificationTypeService, times(1)).findById(id);
    }

    @Test
    void createIdentificationType() {
        // Arrange
        IdentificationTypeDTO requestDto = new IdentificationTypeDTO();

        when(identificationTypeService.save(any())).thenReturn(new IdentificationType());

        // Act
        ResponseEntity<IdentificationTypeDTO> response = identificationTypeController.createIdentificationType(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(identificationTypeService, times(1)).save(any());
    }

    @Test
    void updateIdentificationType() {
        // Arrange
        Long id = 1L;
        IdentificationTypeDTO requestDto = new IdentificationTypeDTO();

        when(identificationTypeService.update(eq(id), any())).thenReturn(Optional.of(new IdentificationType()));

        // Act
        ResponseEntity<IdentificationTypeDTO> response = identificationTypeController.updateIdentificationType(id, requestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(identificationTypeService, times(1)).update(eq(id), any());
    }
}
