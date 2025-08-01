package edu.mx.unsis.unsiSmile.repository.periodontogram;

import edu.mx.unsis.unsiSmile.model.periodontograms.SurfaceMeasurementModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISurfaceMeasurementRepository extends JpaRepository<SurfaceMeasurementModel, Long> {
}