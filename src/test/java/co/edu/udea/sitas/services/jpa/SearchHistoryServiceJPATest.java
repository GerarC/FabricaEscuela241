package co.edu.udea.sitas.services.jpa;

import co.edu.udea.sitas.domain.model.SearchHistory;
import co.edu.udea.sitas.persistence.SearchHistoryRepository;
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

class SearchHistoryServiceJPATest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private SearchHistoryRepository searchHistoryRepository;

    @InjectMocks
    private SearchHistoryServiceJPA searchHistoryServiceJPA;

    @Test
    void testFindAll() {
        // Arrange
        List<SearchHistory> searchHistories = new ArrayList<>();
        when(searchHistoryRepository.findAll()).thenReturn(searchHistories);

        // Act
        List<SearchHistory> result = searchHistoryServiceJPA.findAll();

        // Assert
        assertEquals(searchHistories, result);
    }

    @Test
    void testSave() {
        // Arrange
        SearchHistory searchHistory = new SearchHistory();
        when(searchHistoryRepository.save(searchHistory)).thenReturn(searchHistory);

        // Act
        SearchHistory result = searchHistoryServiceJPA.save(searchHistory);

        // Assert
        assertEquals(searchHistory, result);
    }

    @Test
    void testFindById() {
        // Arrange
        Long id = 1L;
        SearchHistory searchHistory = new SearchHistory();
        when(searchHistoryRepository.findById(id)).thenReturn(Optional.of(searchHistory));

        // Act
        Optional<SearchHistory> result = searchHistoryServiceJPA.findById(id);

        // Assert
        assertEquals(Optional.of(searchHistory), result);
    }

    @Test
    void testUpdate() {
        // Arrange
        Long id = 1L;
        SearchHistory searchHistory = new SearchHistory();
        when(searchHistoryRepository.findById(id)).thenReturn(Optional.of(searchHistory));
        when(searchHistoryRepository.save(searchHistory)).thenReturn(searchHistory);

        // Act
        Optional<SearchHistory> result = searchHistoryServiceJPA.update(id, searchHistory);

        // Assert
        assertEquals(Optional.of(searchHistory), result);
    }

    @Test
    void testDelete() {
        // Arrange
        Long id = 1L;
        SearchHistory searchHistory = new SearchHistory();
        when(searchHistoryRepository.findById(id)).thenReturn(Optional.of(searchHistory));

        // Act
        Optional<SearchHistory> result = searchHistoryServiceJPA.delete(id);

        // Assert
        assertEquals(Optional.of(searchHistory), result);
        verify(searchHistoryRepository, times(1)).deleteById(id);
    }
}

