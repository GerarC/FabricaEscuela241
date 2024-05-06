package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.FlightDTO;
import co.edu.udea.sitas.domain.dto.ScaleDTO;
import co.edu.udea.sitas.domain.model.Flight;
import co.edu.udea.sitas.domain.model.Scale;
import co.edu.udea.sitas.hateoas.FlightHateoasAssembler;
import co.edu.udea.sitas.hateoas.ScaleHateoasAssembler;
import co.edu.udea.sitas.services.FlightService;
import co.edu.udea.sitas.services.ScaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightControllerTest {


    @Mock
    private FlightService flightService;

    @Mock
    private ScaleService scaleService;

    @InjectMocks
    private FlightController flightController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        flightController = new FlightController(flightService, scaleService,
                new FlightHateoasAssembler(), new ScaleHateoasAssembler());
    }

    @Test
    void findALL() {
        // Arrange
        Flight flight = new Flight();
        List<Scale> scales = Arrays.asList(new Scale(), new Scale());
        flight.setScales(scales);
        List<Flight> flights = Collections.singletonList(flight);
        when(flightService.findAll(any())).thenReturn(flights);

        // Act
        ResponseEntity<CollectionModel<FlightDTO>> response = flightController.findALL(new HashMap<>());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(flightService, times(1)).findAll(any());
    }

    @Test
    void findById() {
        // Arrange
        Long id = 1L;
        Flight flight = new Flight();
        List<Scale> scales = Arrays.asList(new Scale(), new Scale());
        flight.setScales(scales);
        when(flightService.findById(id)).thenReturn(Optional.of(flight));

        // Act
        ResponseEntity<FlightDTO> response = flightController.findById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(flightService, times(1)).findById(id);
    }


    @Test
    void findScalesById() {
        // Arrange
        Long id = 1L;
        Flight flight = new Flight();
        List<Scale> scales = Arrays.asList(new Scale(), new Scale());
        flight.setScales(scales);
        when(flightService.findById(id)).thenReturn(Optional.of(flight));

        // Act
        ResponseEntity<CollectionModel<ScaleDTO>> response = flightController.findScalesById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(flightService, times(1)).findById(id);
    }
}