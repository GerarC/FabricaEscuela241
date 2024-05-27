package co.edu.udea.sitas.services;

import co.edu.udea.sitas.domain.model.FlightHistory;

import java.util.List;
import java.util.Optional;

public interface FlightHistoryService {
    List<FlightHistory> findAll();
    FlightHistory save(FlightHistory flightHistory);
    Optional<FlightHistory> findById(Long id);
    Optional<FlightHistory> update(Long id, FlightHistory flightHistory);
    Optional<FlightHistory> delete(Long id);
}
