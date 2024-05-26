package co.edu.udea.sitas.services.jpa;

import co.edu.udea.sitas.domain.model.IdentificationType;
import co.edu.udea.sitas.persistence.IdentificationTypeRepository;
import co.edu.udea.sitas.services.IdentificationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IdentificationTypeServiceJPA implements IdentificationTypeService {

    private final IdentificationTypeRepository identificationTypeRepository;

    @Autowired
    public IdentificationTypeServiceJPA(IdentificationTypeRepository identificationTypeRepository) {
        this.identificationTypeRepository = identificationTypeRepository;
    }

    @Override
    public List<IdentificationType> findAll() {
        return identificationTypeRepository.findAll();
    }

    @Override
    public IdentificationType save(IdentificationType identificationType) {
        return identificationTypeRepository.save(identificationType);
    }

    @Override
    public Optional<IdentificationType> findById(Long id) {
        return identificationTypeRepository.findById(id);
    }

    @Override
    public Optional<IdentificationType> update(Long id, IdentificationType identificationType) {
        Optional<IdentificationType> identificationTypeOptional = identificationTypeRepository.findById(id);
        if (identificationTypeOptional.isPresent()) {
            identificationType.setIdentificationTypeId(id);
            return Optional.of(identificationTypeRepository.save(identificationType));
        }
        return Optional.empty();
    }

    @Override
    public Optional<IdentificationType> delete(Long id) {
        Optional<IdentificationType> identificationTypeOptional = identificationTypeRepository.findById(id);
        if (identificationTypeOptional.isPresent()) {
            identificationTypeRepository.deleteById(id);
        }
        return identificationTypeOptional;
    }
}

