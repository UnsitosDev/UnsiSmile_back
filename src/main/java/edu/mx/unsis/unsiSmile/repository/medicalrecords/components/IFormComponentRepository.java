package edu.mx.unsis.unsiSmile.repository.medicalrecords.components;

import edu.mx.unsis.unsiSmile.model.medicalrecords.components.FormComponentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFormComponentRepository extends JpaRepository<FormComponentModel, Long> {
    Optional<FormComponentModel> findByDescription(String description);
}
