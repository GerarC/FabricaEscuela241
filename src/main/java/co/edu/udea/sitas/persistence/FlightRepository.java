package co.edu.udea.sitas.persistence;

import co.edu.udea.sitas.domain.model.Flight;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends CrudRepository<Flight, Long> {
    List<Flight> findAll(Specification<Flight> spec);
}
