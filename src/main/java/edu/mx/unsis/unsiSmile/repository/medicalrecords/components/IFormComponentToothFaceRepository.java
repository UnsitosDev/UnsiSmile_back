package edu.mx.unsis.unsiSmile.repository.medicalrecords.components;

import edu.mx.unsis.unsiSmile.model.medicalrecords.components.FormComponentToothFaceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFormComponentToothFaceRepository extends JpaRepository<FormComponentToothFaceModel, Long> {
}