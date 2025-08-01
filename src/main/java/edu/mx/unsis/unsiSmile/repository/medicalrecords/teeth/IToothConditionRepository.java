package edu.mx.unsis.unsiSmile.repository.medicalrecords.teeth;

import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothConditionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IToothConditionRepository extends JpaRepository<ToothConditionModel, Long> {

    Optional<ToothConditionModel> findByDescription(String description);
}