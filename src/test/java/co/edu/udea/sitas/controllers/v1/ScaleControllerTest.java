package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.ScaleDTO;
import co.edu.udea.sitas.domain.model.AirplaneModel;
import co.edu.udea.sitas.domain.model.Airport;
import co.edu.udea.sitas.domain.model.Flight;
import co.edu.udea.sitas.domain.model.Scale;
import co.edu.udea.sitas.hateoas.ScaleHateoasAssembler;
import co.edu.udea.sitas.services.ScaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScaleControllerTest {

    @Mock
    private ScaleService scaleService;

    private ScaleController scaleController;

    private ScaleHateoasAssembler assembler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        assembler = new ScaleHateoasAssembler();
        scaleController = new ScaleController(scaleService, assembler);
    }

    @Test
    void getAllScales() {
        Long id = 1L;
        Flight flight = new Flight();
        flight.setFlightId(123L); // Set a non-null flight ID
        AirplaneModel airplaneModel = new AirplaneModel();
        airplaneModel.setAirplaneModel("XYZ123"); // Set a non-null airplane model
        Airport originAirport = new Airport();
        originAirport.setAirportCode("ORIGIN"); // Set a non-null airport code for origin
        Airport destinationAirport = new Airport();
        destinationAirport.setAirportCode("DESTINATION"); // Set a non-null airport code for destination
        LocalDateTime departureDate = LocalDateTime.now(); // Set a non-null departure date
        LocalDateTime arrivalDate = LocalDateTime.now().plusHours(2); // Set a non-null arrival date, assuming 2 hours after departure


        Scale scale = new Scale();
        scale.setScaleId(id);
        scale.setFlight(flight);
        scale.setAirplaneModel(airplaneModel);
        scale.setOriginAirport(originAirport);
        scale.setDestinationAirport(destinationAirport);
        scale.setDepartureDate(departureDate);
        scale.setArrivalDate(arrivalDate);

        CollectionModel<ScaleDTO> expectedDTOs = assembler.toCollectionModel(Collections.singletonList(scale));
        when(scaleService.findAll()).thenReturn(Collections.singletonList(scale));

        // Act
        ResponseEntity<CollectionModel<ScaleDTO>> response = scaleController.getAllScales();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDTOs, response.getBody());
        verify(scaleService, times(1)).findAll();
    }

    @Test
    void getScaleById() {
        Long id = 1L;
        Flight flight = new Flight();
        flight.setFlightId(123L); // Set a non-null flight ID
        AirplaneModel airplaneModel = new AirplaneModel();
        airplaneModel.setAirplaneModel("XYZ123"); // Set a non-null airplane model
        Airport originAirport = new Airport();
        originAirport.setAirportCode("ORIGIN"); // Set a non-null airport code for origin
        Airport destinationAirport = new Airport();
        destinationAirport.setAirportCode("DESTINATION"); // Set a non-null airport code for destination
        LocalDateTime departureDate = LocalDateTime.now(); // Set a non-null departure date
        LocalDateTime arrivalDate = LocalDateTime.now().plusHours(2); // Set a non-null arrival date, assuming 2 hours after departure


        Scale scale = new Scale();
        scale.setScaleId(id);
        scale.setFlight(flight);
        scale.setAirplaneModel(airplaneModel);
        scale.setOriginAirport(originAirport);
        scale.setDestinationAirport(destinationAirport);
        scale.setDepartureDate(departureDate);
        scale.setArrivalDate(arrivalDate);

        when(scaleService.findById(id)).thenReturn(Optional.of(scale));
        ResponseEntity<ScaleDTO> response = scaleController.getScaleById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(scaleService, times(1)).findById(id);
    }

    @Test
    void getScaleById_NotFound() {
        // Arrange
        Long id = 1L;
        when(scaleService.findById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ScaleDTO> response = scaleController.getScaleById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(scaleService, times(1)).findById(id);
    }
}