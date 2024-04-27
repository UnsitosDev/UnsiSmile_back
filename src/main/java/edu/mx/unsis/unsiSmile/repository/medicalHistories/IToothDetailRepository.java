package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.ToothDetailModel;

public interface IToothDetailRepository extends JpaRepository<ToothDetailModel, Long> {
}
