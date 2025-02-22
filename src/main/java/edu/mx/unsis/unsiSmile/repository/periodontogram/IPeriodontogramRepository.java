package edu.mx.unsis.unsiSmile.repository.periodontogram;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.periodontograms.PeriodontogramModel;

public interface IPeriodontogramRepository extends JpaRepository<PeriodontogramModel, Long> {

}
