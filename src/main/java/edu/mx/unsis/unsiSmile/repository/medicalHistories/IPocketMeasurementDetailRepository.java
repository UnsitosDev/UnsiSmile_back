package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.PocketMeasurementDetailModel;

public interface IPocketMeasurementDetailRepository extends JpaRepository<PocketMeasurementDetailModel, Long> {
}

