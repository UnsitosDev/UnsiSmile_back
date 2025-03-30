package edu.mx.unsis.unsiSmile.repository.medicalHistories.teeth;

import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.FormComponentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFormComponentRepository extends JpaRepository<FormComponentModel, Long> {
}
