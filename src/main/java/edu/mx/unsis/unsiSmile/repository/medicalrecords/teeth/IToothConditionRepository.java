package edu.mx.unsis.unsiSmile.repository.medicalrecords.teeth;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothConditionModel;
import org.springframework.stereotype.Repository;

@Repository
public interface IToothConditionRepository extends JpaRepository<ToothConditionModel, Long> {
    Optional<ToothConditionModel> findByDescription(String description);
}