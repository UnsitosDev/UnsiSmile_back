package edu.mx.unsis.unsiSmile.repository.medicalrecords.fluorosis;

import edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis.FluorosisToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis.FluorosisToothConditionAssignmentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFluorosisToothConditionAssignmentRepository extends JpaRepository<FluorosisToothConditionAssignmentModel, FluorosisToothConditionAssignmentId> {
}
