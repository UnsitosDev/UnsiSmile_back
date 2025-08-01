package edu.mx.unsis.unsiSmile.repository.medicalHistories.teeth;

import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.FormComponentModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.FormComponentToothfaceConditionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFormComponentToothfaceConditionRepository extends JpaRepository<FormComponentToothfaceConditionModel, Long> {
    List<FormComponentToothfaceConditionModel> findByFormComponent(FormComponentModel formComponent);
}
