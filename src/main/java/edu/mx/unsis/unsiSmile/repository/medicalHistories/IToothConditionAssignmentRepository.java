package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.medicalHistories.ToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.utils.ToothConditionAssignmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IToothConditionAssignmentRepository extends JpaRepository<ToothConditionAssignmentModel, ToothConditionAssignmentId> {
}