package edu.mx.unsis.unsiSmile.repository.medicalHistories.teeth;

import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.ToothFaceConditionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IToothFaceConditionRepository extends JpaRepository<ToothFaceConditionModel, Long> {
    Optional<ToothFaceConditionModel> findByDescription(String description);
}