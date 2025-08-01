package edu.mx.unsis.unsiSmile.repository.medicalrecords.odontograms;

import edu.mx.unsis.unsiSmile.model.medicalrecords.odontogram.ToothFaceConditionAssignmentId;
import edu.mx.unsis.unsiSmile.model.medicalrecords.odontogram.ToothfaceConditionsAssignmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IToothFaceConditionAssignmentRepository extends JpaRepository<ToothfaceConditionsAssignmentModel, ToothFaceConditionAssignmentId> {
}