package edu.mx.unsis.unsiSmile.repository.periodontogram;

import edu.mx.unsis.unsiSmile.model.periodontograms.ToothEvaluationModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IToothEvaluationRepository extends JpaRepository<ToothEvaluationModel, Long> {
}