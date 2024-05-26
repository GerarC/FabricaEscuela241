package co.edu.udea.sitas.services.jpa;

import co.edu.udea.sitas.domain.model.FlightHistory;
import co.edu.udea.sitas.persistence.FlightHistoryRepository;
import co.edu.udea.sitas.services.FlightHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightHistoryServiceJPA implements FlightHistoryService {

    private final FlightHistoryRepository flightHistoryRepository;

    @Autowired
    public FlightHistoryServiceJPA(FlightHistoryRepository flightHistoryRepository) {
        this.flightHistoryRepository = flightHistoryRepository;
    }

    @Override
    public List<FlightHistory> findAll() {
        return flightHistoryRepository.findAll();
    }

    @Override
    public FlightHistory save(FlightHistory flightHistory) {
        return flightHistoryRepository.save(flightHistory);
    }

    @Override
    public Optional<FlightHistory> findById(Long id) {
        return flightHistoryRepository.findById(id);
    }

    @Override
    public Optional<FlightHistory> update(Long id, FlightHistory flightHistory) {
        Optional<FlightHistory> flightHistoryOptional = flightHistoryRepository.findById(id);
        if (flightHistoryOptional.isPresent()) {
            flightHistory.setFlightHistoryId(id);
            return Optional.of(flightHistoryRepository.save(flightHistory));
        }
        return Optional.empty();
    }

    @Override
    public Optional<FlightHistory> delete(Long id) {
        Optional<FlightHistory> flightHistoryOptional = flightHistoryRepository.findById(id);
        if (flightHistoryOptional.isPresent()) {
            flightHistoryRepository.deleteById(id);
        }
        return flightHistoryOptional;
    }
}

