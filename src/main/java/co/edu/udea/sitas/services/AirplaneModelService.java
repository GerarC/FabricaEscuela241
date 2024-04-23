package co.edu.udea.sitas.services;

import co.edu.udea.sitas.domain.model.AirplaneModel;

import java.util.List;
import java.util.Optional;

public interface AirplaneModelService {
    List<AirplaneModel> findAll();
    AirplaneModel save(AirplaneModel airplaneModel);
    Optional<AirplaneModel> findById(String airplaneModel);
    Optional<AirplaneModel> update(String model, AirplaneModel airplaneModel);
    Optional<AirplaneModel> delete(String airplaneModel);
}
