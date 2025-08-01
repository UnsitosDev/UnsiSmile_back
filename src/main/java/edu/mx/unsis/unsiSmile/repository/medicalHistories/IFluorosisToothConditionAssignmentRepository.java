package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis.FluorosisToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis.FluorosisToothConditionAssignmentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFluorosisToothConditionAssignmentRepository extends JpaRepository<FluorosisToothConditionAssignmentModel, FluorosisToothConditionAssignmentId> {
}
