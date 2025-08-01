package edu.mx.unsis.unsiSmile.repository.periodontogram;

import edu.mx.unsis.unsiSmile.model.periodontograms.SurfaceEvaluationModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISurfaceEvaluationRepository extends JpaRepository<SurfaceEvaluationModel, Long> {
}