package co.edu.udea.sitas.services.jpa;

import co.edu.udea.sitas.domain.model.IdentificationType;
import co.edu.udea.sitas.persistence.IdentificationTypeRepository;
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

class IdentificationTypeServiceJPATest {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private IdentificationTypeRepository identificationTypeRepository;

    @InjectMocks
    private IdentificationTypeServiceJPA identificationTypeServiceJPA;

    @Test
    void testFindAll() {
        // Arrange
        List<IdentificationType> identificationTypes = new ArrayList<>();
        when(identificationTypeRepository.findAll()).thenReturn(identificationTypes);

        // Act
        List<IdentificationType> result = identificationTypeServiceJPA.findAll();

        // Assert
        assertEquals(identificationTypes, result);
    }

    @Test
    void testSave() {
        // Arrange
        IdentificationType identificationType = new IdentificationType();
        when(identificationTypeRepository.save(identificationType)).thenReturn(identificationType);

        // Act
        IdentificationType result = identificationTypeServiceJPA.save(identificationType);

        // Assert
        assertEquals(identificationType, result);
    }

    @Test
    void testFindById() {
        // Arrange
        Long id = 1L;
        IdentificationType identificationType = new IdentificationType();
        when(identificationTypeRepository.findById(id)).thenReturn(Optional.of(identificationType));

        // Act
        Optional<IdentificationType> result = identificationTypeServiceJPA.findById(id);

        // Assert
        assertEquals(Optional.of(identificationType), result);
    }

    @Test
    void testUpdate() {
        // Arrange
        Long id = 1L;
        IdentificationType identificationType = new IdentificationType();
        when(identificationTypeRepository.findById(id)).thenReturn(Optional.of(identificationType));
        when(identificationTypeRepository.save(identificationType)).thenReturn(identificationType);

        // Act
        Optional<IdentificationType> result = identificationTypeServiceJPA.update(id, identificationType);

        // Assert
        assertEquals(Optional.of(identificationType), result);
    }

    @Test
    void testDelete() {
        // Arrange
        Long id = 1L;
        IdentificationType identificationType = new IdentificationType();
        when(identificationTypeRepository.findById(id)).thenReturn(Optional.of(identificationType));

        // Act
        Optional<IdentificationType> result = identificationTypeServiceJPA.delete(id);

        // Assert
        assertEquals(Optional.of(identificationType), result);
        verify(identificationTypeRepository, times(1)).deleteById(id);
    }
}

