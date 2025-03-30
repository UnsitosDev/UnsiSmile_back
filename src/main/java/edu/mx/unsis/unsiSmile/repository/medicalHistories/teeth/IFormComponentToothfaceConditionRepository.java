package edu.mx.unsis.unsiSmile.repository.medicalHistories.teeth;

import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.FormComponentToothfaceConditionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFormComponentToothfaceConditionRepository extends JpaRepository<FormComponentToothfaceConditionModel, Long> {
}
