package co.edu.udea.sitas.persistence;

import co.edu.udea.sitas.domain.model.IdentificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentificationTypeRepository extends JpaRepository<IdentificationType, Long> {
}

