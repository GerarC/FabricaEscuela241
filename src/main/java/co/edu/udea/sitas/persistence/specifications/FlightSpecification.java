package co.edu.udea.sitas.persistence.specifications;

import co.edu.udea.sitas.domain.model.Airport;
import co.edu.udea.sitas.domain.model.Flight;
import co.edu.udea.sitas.domain.model.Scale;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

@Slf4j
public class FlightSpecification {

    /**
     * Builds a specification that searches flights with a given flight ID.
     *
     * @param flightId the flight ID to search flights
     * @return the flight specification
     */
    public static Specification<Flight> withFlightId(Long flightId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("flightId"), flightId);
    }

    /**
     * This builds a specification to search flights that their departure date is after a given date,
     *
     * @param date limit date to search flights
     * @return the built specification query
     */
    public static Specification<Flight> flightsDepartureAfter(LocalDateTime date) {
        return (Root<Flight> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Subquery<LocalDateTime> earliestDepartureSubquery = query.subquery(LocalDateTime.class);
            Root<Scale> scale = earliestDepartureSubquery.from(Scale.class);
            Expression<LocalDateTime> earliestDeparture = scaleFetchFirst(scale, builder, root, earliestDepartureSubquery, true);
            return builder.greaterThanOrEqualTo(earliestDeparture, date);
        };
    }

    /**
     * This builds a specification to search flights which their arrival date is before a given date
     * @param date limit date to search flights
     * @return the built specification query
     */
    public static Specification<Flight> flightsDepartureBefore(LocalDateTime date) {
        return (Root<Flight> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Subquery<LocalDateTime> latestDepartureSubquery = query.subquery(LocalDateTime.class);
            Root<Scale> scale = latestDepartureSubquery.from(Scale.class);
            latestDepartureSubquery.select(scale.get("arrivalDate"))
                    .where(builder.equal(scale.get("flight"), root));
            Expression<LocalDateTime> latestDeparture = scaleFetchFirst(scale, builder, root, latestDepartureSubquery, false);
            return builder.lessThanOrEqualTo(latestDeparture, date);
        };
    }

    /**
     * Builds a specification that search flights with a given flight number
     * @param flightNumber the param to search flights
     * @return the flight specification
     */
    public static Specification<Flight> withFlightNumber(String flightNumber) {
        return (Root<Flight> root, CriteriaQuery<?> query, CriteriaBuilder builder) ->
                builder.equal(root.get("flightNumber"), flightNumber);
    }

    /**
     * Returns a Specification to filter flights with the specified origin city.
     *
     * @param city The origin city to filter flights by.
     * @return A Specification for filtering flights by origin city.
     */
    public static Specification<Flight> withOriginCity(String city) {
        return (root, query, builder) -> {
            Subquery<String> originCitySubquery = createAirportSubquery(query, root, builder, "min", "originAirport", "city");
            return builder.equal(originCitySubquery.getSelection(), city);
        };
    }

    /**
     * Returns a Specification to filter flights with the specified destination city.
     *
     * @param city The destination city to filter flights by.
     * @return A Specification for filtering flights by destination city.
     */
    public static Specification<Flight> withDestinationCity(String city) {
        return (root, query, builder) -> {
            Subquery<String> destinationCitySubquery = createAirportSubquery(query, root, builder, "max", "destinationAirport", "city");
            return builder.equal(destinationCitySubquery.getSelection(), city);
        };
    }

    /**
     * Returns a Specification to filter flights with the specified origin airport code.
     *
     * @param airportCode The origin airport code to filter flights by.
     * @return A Specification for filtering flights by origin airport code.
     */
    public static Specification<Flight> withOriginAirport(String airportCode) {
        return (root, query, builder) -> {
            Subquery<String> originAirportSubquery = createAirportSubquery(query, root, builder, "min", "originAirport", "airportCode");
            return builder.equal(originAirportSubquery.getSelection(), airportCode);
        };
    }

    /**
     * Returns a Specification to filter flights with the specified destination airport code.
     *
     * @param airportCode The destination airport code to filter flights by.
     * @return A Specification for filtering flights by destination airport code.
     */
    public static Specification<Flight> withDestinationAirport(String airportCode) {
        return (root, query, builder) -> {
            Subquery<String> destinationAirportSubquery = createAirportSubquery(query, root, builder, "max", "destinationAirport", "airportCode");
            return builder.equal(destinationAirportSubquery.getSelection(), airportCode);
        };
    }

    /**
     * Builds a specification that search flights with a min price limit
     * @param minPrice the param to search flights
     * @return the flight specification
     */
    public static Specification<Flight> withBasePriceGreaterThan(Float minPrice) {
        return (Root<Flight> root, CriteriaQuery<?> query, CriteriaBuilder builder) ->
                builder.greaterThanOrEqualTo(root.get("basePrice"), minPrice);
    }

    /**
     * Builds a specification that search flights with a max price limit
     * @param maxPrice the param to search flights
     * @return the flight specification
     */
    public static Specification<Flight> withBasePriceLessThan(Float maxPrice) {
        return (Root<Flight> root, CriteriaQuery<?> query, CriteriaBuilder builder) ->
                builder.lessThanOrEqualTo(root.get("basePrice"), maxPrice);
    }


    private static Expression<LocalDateTime> scaleFetchFirst(Root<Scale> scale, CriteriaBuilder builder,
                                                             Root<Flight> root,
                                                             Subquery<LocalDateTime> subquery,
                                                             boolean asc) {
        subquery.select(builder.function(asc ? "min" : "max", LocalDateTime.class, scale.get("departureDate")))
                .where(builder.equal(scale.get("flight"), root));
        return subquery.getSelection();
    }

    /**
     * Creates a subquery to find the minimum or maximum departure date for each flight.
     *
     * @param query   The CriteriaQuery instance.
     * @param root    The Root of the Flight entity.
     * @param builder The CriteriaBuilder instance.
     * @param function The aggregate function to apply (e.g., "min" or "max").
     * @return The subquery to find the minimum or maximum departure date.
     */
    private static Subquery<LocalDateTime> createDepartureDateSubquery(CriteriaQuery<?> query, Root<Flight> root, CriteriaBuilder builder, String function) {
        log.info("Search {} date", function);
        Subquery<LocalDateTime> departureDateSubquery = query.subquery(LocalDateTime.class);
        Root<Scale> scaleSubqueryRoot = departureDateSubquery.from(Scale.class);
        departureDateSubquery.select(builder.function(function, LocalDateTime.class, scaleSubqueryRoot.get("departureDate")))
                .where(builder.equal(scaleSubqueryRoot.get("flight"), root));
        return departureDateSubquery;
    }

    /**
     * Creates a subquery to find the city or airport code corresponding to the minimum or maximum departure date.
     *
     * @param query        The CriteriaQuery instance.
     * @param root         The Root of the Flight entity.
     * @param builder      The CriteriaBuilder instance.
     * @param function     The aggregate function to apply (e.g., "min" or "max").
     * @param airportType  The type of airport (e.g., "originAirport" or "destinationAirport").
     * @param attributeName The name of the attribute to select (e.g., "city" or "airportCode").
     * @return The subquery to find the city or airport code.
     */
    private static Subquery<String> createAirportSubquery(CriteriaQuery<?> query, Root<Flight> root, CriteriaBuilder builder, String function, String airportType, String attributeName) {
        log.info("search {} in a {}", attributeName, airportType);
        Subquery<String> airportSubquery = query.subquery(String.class);
        Root<Scale> scaleRoot = airportSubquery.from(Scale.class);
        Join<Scale, Airport> airportJoin = scaleRoot.join(airportType);
        airportSubquery.select(airportJoin.get(attributeName))
                .where(builder.equal(scaleRoot.get("departureDate"), createDepartureDateSubquery(query, root, builder, function).getSelection()));
        return airportSubquery;
    }
}