package co.edu.udea.sitas.persistence;

import co.edu.udea.sitas.domain.model.Airport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends CrudRepository<Airport, String> {
}
