package edu.mx.unsis.unsiSmile.repository.medicalrecords.teeth;

import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothFaceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IToothFaceRepository extends JpaRepository<ToothFaceModel, String> {
}