package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.FlightHistoryDTO;
import co.edu.udea.sitas.domain.model.Flight;
import co.edu.udea.sitas.domain.model.FlightHistory;
import co.edu.udea.sitas.domain.model.Person;
import co.edu.udea.sitas.services.FlightHistoryService;
import co.edu.udea.sitas.services.FlightService;
import co.edu.udea.sitas.services.PersonService;
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

class FlightHistoryControllerTest {

    @Mock
    private FlightHistoryService flightHistoryService;

    @Mock
    private FlightService flightService;

    @Mock
    private PersonService personService;

    @InjectMocks
    private FlightHistoryController flightHistoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllFlightHistories() {
        // Arrange
        Person person = new Person();
        person.setPersonId(1L);
        Flight flight = new Flight();
        flight.setFlightId(1L);
        FlightHistory flightHistory1 = new FlightHistory();
        flightHistory1.setPerson(person);
        flightHistory1.setFlight(flight);
        FlightHistory flightHistory2 = new FlightHistory();
        flightHistory2.setPerson(person);
        flightHistory2.setFlight(flight);

        when(flightHistoryService.findAll()).thenReturn(Arrays.asList(flightHistory1, flightHistory2));

        // Act
        ResponseEntity<List<FlightHistoryDTO>> response = flightHistoryController.getAllFlightHistories();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(flightHistoryService, times(1)).findAll();
    }

    @Test
    void getFlightHistoryById() {
        // Arrange
        Long id = 1L;
        Person person = new Person();
        person.setPersonId(1L);
        Flight flight = new Flight();
        flight.setFlightId(1L);
        FlightHistory flightHistory = new FlightHistory();
        flightHistory.setPerson(person);
        flightHistory.setFlight(flight);

        when(flightHistoryService.findById(id)).thenReturn(Optional.of(flightHistory));

        // Act
        ResponseEntity<FlightHistoryDTO> response = flightHistoryController.getFlightHistoryById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(flightHistoryService, times(1)).findById(id);
    }

    @Test
    void createFlightHistory() {
        // Arrange
        FlightHistoryDTO requestDto = new FlightHistoryDTO();
        requestDto.setFlightId(1L);
        requestDto.setPersonId(1L);

        Person person = new Person();
        person.setPersonId(1L);
        Flight flight = new Flight();
        flight.setFlightId(1L);
        FlightHistory flightHistory = new FlightHistory();
        flightHistory.setPerson(person);
        flightHistory.setFlight(flight);

        when(flightService.findById(requestDto.getFlightId())).thenReturn(Optional.of(flight));
        when(personService.findById(requestDto.getPersonId())).thenReturn(Optional.of(person));
        when(flightHistoryService.save(any())).thenReturn(flightHistory);

        // Act
        ResponseEntity<FlightHistoryDTO> response = flightHistoryController.createFlightHistory(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(flightHistoryService, times(1)).save(any());
    }

    @Test
    void updateFlightHistory() {
        // Arrange
        Long id = 1L;
        FlightHistoryDTO requestDto = new FlightHistoryDTO();
        requestDto.setFlightId(1L);
        requestDto.setPersonId(1L);

        Person person = new Person();
        person.setPersonId(1L);
        Flight flight = new Flight();
        flight.setFlightId(1L);
        FlightHistory flightHistory = new FlightHistory();
        flightHistory.setPerson(person);
        flightHistory.setFlight(flight);

        when(flightService.findById(requestDto.getFlightId())).thenReturn(Optional.of(flight));
        when(personService.findById(requestDto.getPersonId())).thenReturn(Optional.of(person));
        when(flightHistoryService.update(eq(id), any())).thenReturn(Optional.of(flightHistory));

        // Act
        ResponseEntity<FlightHistoryDTO> response = flightHistoryController.updateFlightHistory(id, requestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(flightHistoryService, times(1)).update(eq(id), any());
    }
}


