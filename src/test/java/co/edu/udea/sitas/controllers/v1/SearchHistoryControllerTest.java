package co.edu.udea.sitas.controllers.v1;

import co.edu.udea.sitas.domain.dto.SearchHistoryDTO;
import co.edu.udea.sitas.domain.model.Person;
import co.edu.udea.sitas.domain.model.Scale;
import co.edu.udea.sitas.domain.model.SearchHistory;
import co.edu.udea.sitas.services.PersonService;
import co.edu.udea.sitas.services.ScaleService;
import co.edu.udea.sitas.services.SearchHistoryService;
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

class SearchHistoryControllerTest {

    @Mock
    private SearchHistoryService searchHistoryService;

    @Mock
    private PersonService personService;

    @Mock
    private ScaleService scaleService;

    @InjectMocks
    private SearchHistoryController searchHistoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllSearchHistories() {
        // Arrange
        Person person = new Person();
        person.setPersonId(1L);
        Scale scale = new Scale();
        scale.setScaleId(1L);
        SearchHistory searchHistory1 = new SearchHistory();
        searchHistory1.setPerson(person);
        searchHistory1.setScale(scale);
        SearchHistory searchHistory2 = new SearchHistory();
        searchHistory2.setPerson(person);
        searchHistory2.setScale(scale);

        when(searchHistoryService.findAll()).thenReturn(Arrays.asList(searchHistory1, searchHistory2));

        // Act
        ResponseEntity<List<SearchHistoryDTO>> response = searchHistoryController.getAllSearchHistories();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(searchHistoryService, times(1)).findAll();
    }

    @Test
    void getSearchHistoryById() {
        // Arrange
        Long id = 1L;
        Person person = new Person();
        person.setPersonId(1L);
        Scale scale = new Scale();
        scale.setScaleId(1L);
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setPerson(person);
        searchHistory.setScale(scale);

        when(searchHistoryService.findById(id)).thenReturn(Optional.of(searchHistory));

        // Act
        ResponseEntity<SearchHistoryDTO> response = searchHistoryController.getSearchHistoryById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(searchHistoryService, times(1)).findById(id);
    }

    @Test
    void createSearchHistory() {
        // Arrange
        SearchHistoryDTO requestDto = new SearchHistoryDTO();
        requestDto.setScaleId(1L);
        requestDto.setPersonId(1L);

        Person person = new Person();
        person.setPersonId(1L);
        Scale scale = new Scale();
        scale.setScaleId(1L);
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setPerson(person);
        searchHistory.setScale(scale);

        when(scaleService.findById(requestDto.getScaleId())).thenReturn(Optional.of(scale));
        when(personService.findById(requestDto.getPersonId())).thenReturn(Optional.of(person));
        when(searchHistoryService.save(any())).thenReturn(searchHistory);

        // Act
        ResponseEntity<SearchHistoryDTO> response = searchHistoryController.createSearchHistory(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(searchHistoryService, times(1)).save(any());
    }

    @Test
    void updateSearchHistory() {
        // Arrange
        Long id = 1L;
        SearchHistoryDTO requestDto = new SearchHistoryDTO();
        requestDto.setScaleId(1L);
        requestDto.setPersonId(1L);

        Person person = new Person();
        person.setPersonId(1L);
        Scale scale = new Scale();
        scale.setScaleId(1L);
        SearchHistory searchHistory = new SearchHistory();
        searchHistory.setPerson(person);
        searchHistory.setScale(scale);

        when(scaleService.findById(requestDto.getScaleId())).thenReturn(Optional.of(scale));
        when(personService.findById(requestDto.getPersonId())).thenReturn(Optional.of(person));
        when(searchHistoryService.update(eq(id), any())).thenReturn(Optional.of(searchHistory));

        // Act
        ResponseEntity<SearchHistoryDTO> response = searchHistoryController.updateSearchHistory(id, requestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(searchHistoryService, times(1)).update(eq(id), any());
    }
}


