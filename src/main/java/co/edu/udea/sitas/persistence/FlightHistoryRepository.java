package co.edu.udea.sitas.persistence;

import co.edu.udea.sitas.domain.model.FlightHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightHistoryRepository extends JpaRepository<FlightHistory, Long> {
}

