package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ToothFaceConditionModel;
import edu.mx.unsis.unsiSmile.model.utils.ToothFaceConditionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IToothFaceConditionRepository extends JpaRepository<ToothFaceConditionModel, ToothFaceConditionId> {

}