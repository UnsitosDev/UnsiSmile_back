package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.medicalHistories.fluorosis.FluorosisToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.utils.FluorosisToothConditionAssignmentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFluorosisToothConditionAssignmentRepository extends JpaRepository<FluorosisToothConditionAssignmentModel, FluorosisToothConditionAssignmentId> {
}
