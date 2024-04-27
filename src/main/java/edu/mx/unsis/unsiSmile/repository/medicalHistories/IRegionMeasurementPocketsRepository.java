package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.RegionMeasurementPocketsModel;

public interface IRegionMeasurementPocketsRepository extends JpaRepository<RegionMeasurementPocketsModel, Long> {
    
}
