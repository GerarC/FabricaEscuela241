package co.edu.udea.sitas.services;

import co.edu.udea.sitas.domain.model.IdentificationType;

import java.util.List;
import java.util.Optional;

public interface IdentificationTypeService {
    List<IdentificationType> findAll();
    IdentificationType save(IdentificationType identificationType);
    Optional<IdentificationType> findById(Long id);
    Optional<IdentificationType> update(Long id, IdentificationType identificationType);
    Optional<IdentificationType> delete(Long id);
}

