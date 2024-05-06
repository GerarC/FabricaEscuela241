package co.edu.udea.sitas.persistence;

import co.edu.udea.sitas.domain.model.AirplaneModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirplaneModelRepository extends CrudRepository<AirplaneModel, String> {
}
