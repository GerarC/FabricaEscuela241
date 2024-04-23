package co.edu.udea.sitas.services.jpa;

import co.edu.udea.sitas.domain.model.Airport;
import co.edu.udea.sitas.persistence.AirportRepository;
import co.edu.udea.sitas.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportServiceJPA implements AirportService {
    private final AirportRepository airportRepository;

    @Autowired
    public AirportServiceJPA(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public List<Airport> findAll() {
        return airportRepository.findAll();
    }

    @Override
    public Airport save(Airport airport) {
        return airportRepository.save(airport);
    }

    @Override
    public Optional<Airport> findById(String airportCode) {
        return airportRepository.findById(airportCode);
    }

    @Override
    public Optional<Airport> update(String airportCode, Airport airport) {
        Optional<Airport> airportOptional = airportRepository.findById(airportCode);
        if (airportOptional.isPresent()) {
            airport.setAirportCode(airportCode);
            return Optional.of(airportRepository.save(airport));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Airport> delete(String airportCode) {
        Optional<Airport> airportOptional = airportRepository.findById(airportCode);
        if (airportOptional.isPresent()) {
            airportRepository.deleteById(airportCode);
        }
        return airportOptional;
    }
}
