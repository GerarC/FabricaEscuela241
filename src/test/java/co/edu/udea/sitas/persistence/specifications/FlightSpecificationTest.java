package co.edu.udea.sitas.persistence.specifications;

import co.edu.udea.sitas.domain.model.Flight;
import co.edu.udea.sitas.domain.model.Scale;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class FlightSpecificationTest {

    @Mock
    private Root<Flight> root;

    @Mock
    private CriteriaQuery<?> query;

    @Mock
    private CriteriaBuilder builder;

    @Mock
    private Subquery<LocalDateTime> subquery;

    @Mock
    private Root<Scale> scale;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testWithFlightId() {
        Long flightId = 123L;
        Specification<Flight> specification = FlightSpecification.withFlightId(flightId);

        specification.toPredicate(root, query, builder);

        verify(builder).equal(root.get("flightId"), flightId);
    }

    @Test
    void testFlightsDepartureAfter() {
        LocalDateTime date = LocalDateTime.now();
        Specification<Flight> specification = FlightSpecification.flightsDepartureAfter(date);

        when(query.subquery(LocalDateTime.class)).thenReturn(subquery);
        when(subquery.from(Scale.class)).thenReturn(scale);
        when(subquery.select(scale.get("arrivalDate"))).thenReturn(subquery); // Mocking select method to return the same subquery

        specification.toPredicate(root, query, builder);

        verify(builder).greaterThanOrEqualTo(eq(null), eq(date));
    }

    @Test
    void testFlightsDepartureBefore() {
        LocalDateTime date = LocalDateTime.now();
        Specification<Flight> specification = FlightSpecification.flightsDepartureBefore(date);

        when(query.subquery(LocalDateTime.class)).thenReturn(subquery);
        when(subquery.from(Scale.class)).thenReturn(scale);
        when(subquery.select(scale.get("arrivalDate"))).thenReturn(subquery); // Mocking select method to return the same subquery

        specification.toPredicate(root, query, builder);

        verify(builder).lessThanOrEqualTo(eq(null), eq(date));
    }

    @Test
    void testWithFlightNumber() {
        String flightNumber = "ABC123";
        Specification<Flight> specification = FlightSpecification.withFlightNumber(flightNumber);

        specification.toPredicate(root, query, builder);

        verify(builder).equal(root.get("flightNumber"), flightNumber);
    }

    @Test
    void testWithBasePriceGreaterThan() {
        Float minPrice = 100.0f;
        Specification<Flight> specification = FlightSpecification.withBasePriceGreaterThan(minPrice);

        specification.toPredicate(root, query, builder);

        verify(builder).greaterThanOrEqualTo(root.get("basePrice"), minPrice);
    }

    @Test
    void testWithBasePriceLessThan() {
        Float maxPrice = 500.0f;
        Specification<Flight> specification = FlightSpecification.withBasePriceLessThan(maxPrice);

        specification.toPredicate(root, query, builder);

        verify(builder).lessThanOrEqualTo(root.get("basePrice"), maxPrice);
    }

}