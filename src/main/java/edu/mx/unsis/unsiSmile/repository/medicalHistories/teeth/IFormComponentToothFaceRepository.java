package edu.mx.unsis.unsiSmile.repository.medicalHistories.teeth;

import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.FormComponentToothFaceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFormComponentToothFaceRepository extends JpaRepository<FormComponentToothFaceModel, Long> {
}