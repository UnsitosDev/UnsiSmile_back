package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ToothFaceConditionModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ToothfaceConditionsAssignmentModel;
import edu.mx.unsis.unsiSmile.model.utils.ToothFaceConditionAssignmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IToothFaceConditionAssignmentRepository extends JpaRepository<ToothfaceConditionsAssignmentModel, ToothFaceConditionAssignmentId> {

}