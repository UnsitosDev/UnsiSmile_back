package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ProphylaxisToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.utils.ProphylaxisToothConditionAssignmentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProphylaxisToothConditionAssignmentRepository extends JpaRepository<ProphylaxisToothConditionAssignmentModel, ProphylaxisToothConditionAssignmentId> {
}
