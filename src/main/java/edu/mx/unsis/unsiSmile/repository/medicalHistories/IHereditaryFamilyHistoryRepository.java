package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.HereditaryFamilyHistoryModel;

public interface IHereditaryFamilyHistoryRepository extends JpaRepository<HereditaryFamilyHistoryModel, Long> {
}
