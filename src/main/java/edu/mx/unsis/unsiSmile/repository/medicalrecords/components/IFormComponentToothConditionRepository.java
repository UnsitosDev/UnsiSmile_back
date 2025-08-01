package edu.mx.unsis.unsiSmile.repository.medicalrecords.components;

import edu.mx.unsis.unsiSmile.model.medicalrecords.components.FormComponentModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.components.FormComponentToothConditionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFormComponentToothConditionRepository extends JpaRepository<FormComponentToothConditionModel, Long> {

    List<FormComponentToothConditionModel> findByFormComponent(FormComponentModel formComponent);
}