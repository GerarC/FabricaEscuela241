package co.edu.udea.sitas.services.jpa;

import co.edu.udea.sitas.domain.model.AirplaneModel;
import co.edu.udea.sitas.persistence.AirplaneModelRepository;
import co.edu.udea.sitas.services.AirplaneModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirplaneModelServiceJPA implements AirplaneModelService {

    private final AirplaneModelRepository airplaneModelRepository;

    @Autowired
    public AirplaneModelServiceJPA(AirplaneModelRepository airplaneModelRepository) {
        this.airplaneModelRepository = airplaneModelRepository;
    }

    @Override
    public List<AirplaneModel> findAll() {
        return (List<AirplaneModel>) airplaneModelRepository.findAll();
    }

    @Override
    public AirplaneModel save(AirplaneModel airplaneModel) {
        return airplaneModelRepository.save(airplaneModel);
    }

    @Override
    public Optional<AirplaneModel> findById(String model) {
        return airplaneModelRepository.findById(model);
    }

    @Override
    public Optional<AirplaneModel> update(String model, AirplaneModel airplaneModel) {
        Optional<AirplaneModel> airplaneModelOptional = airplaneModelRepository.findById(model);
        if (airplaneModelOptional.isPresent()) {
            airplaneModel.setAirplaneModel(model);
            return Optional.of(airplaneModelRepository.save(airplaneModel));
        }
        return Optional.empty();
    }

    @Override
    public Optional<AirplaneModel> delete(String model) {
        Optional<AirplaneModel> airplaneModelOptional = airplaneModelRepository.findById(model);
        if (airplaneModelOptional.isPresent()) {
            airplaneModelRepository.deleteById(model);
        }
        return airplaneModelOptional;
    }
}