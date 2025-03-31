package edu.mx.unsis.unsiSmile.repository.medicalHistories.teeth;

import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.FormComponentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFormComponentRepository extends JpaRepository<FormComponentModel, Long> {
    Optional<FormComponentModel> findByDescription(String description);
}
