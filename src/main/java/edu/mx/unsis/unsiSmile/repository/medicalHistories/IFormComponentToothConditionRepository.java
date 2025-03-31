package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.FormComponentModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.FormComponentToothConditionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFormComponentToothConditionRepository extends JpaRepository<FormComponentToothConditionModel, Long> {
    List<FormComponentToothConditionModel> findByFormComponent(FormComponentModel formComponent);
}