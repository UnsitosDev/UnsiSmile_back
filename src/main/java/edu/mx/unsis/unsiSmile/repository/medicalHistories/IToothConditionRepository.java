package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.ToothConditionModel;

public interface IToothConditionRepository extends JpaRepository<ToothConditionModel, Long> {
    Optional<ToothConditionModel> findByDescription(String description);
}
