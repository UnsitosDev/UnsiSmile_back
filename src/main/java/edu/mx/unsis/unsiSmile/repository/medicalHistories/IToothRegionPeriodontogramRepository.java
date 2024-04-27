package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.ToothRegionPeriodontogramModel;

public interface IToothRegionPeriodontogramRepository extends JpaRepository<ToothRegionPeriodontogramModel, Long> {
}
