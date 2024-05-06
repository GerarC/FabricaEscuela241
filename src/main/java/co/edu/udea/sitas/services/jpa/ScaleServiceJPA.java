package co.edu.udea.sitas.services.jpa;

import co.edu.udea.sitas.domain.model.Flight;
import co.edu.udea.sitas.domain.model.Scale;
import co.edu.udea.sitas.persistence.ScaleRepository;
import co.edu.udea.sitas.services.ScaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScaleServiceJPA implements ScaleService {

    private final ScaleRepository scaleRepository;

    @Autowired
    public ScaleServiceJPA(ScaleRepository scaleRepository) {
        this.scaleRepository = scaleRepository;
    }

    @Override
    public List<Scale> findAll() {
        return (List<Scale>) scaleRepository.findAll();
    }

    @Override
    public Scale save(Scale scale) {
        return scaleRepository.save(scale);
    }

    @Override
    public Optional<Scale> findById(Long id) {
        return scaleRepository.findById(id);
    }

    @Override
    public Optional<Scale> update(Long id, Scale scale) {
        Optional<Scale> scaleToUpdate = scaleRepository.findById(id);
        if(scaleToUpdate.isPresent()) return Optional.of(scaleRepository.save(scale));
        else return Optional.empty();
    }

    @Override
    public Optional<Scale> delete(Long id) {
        Optional<Scale> deletedScale = scaleRepository.findById(id);
        if(deletedScale.isPresent()) scaleRepository.deleteById(id);
        return deletedScale;
    }

    @Override
    public List<Scale> findAllByFlight(Flight flight) {
        return scaleRepository.findAllByFlight(flight);
    }
}
