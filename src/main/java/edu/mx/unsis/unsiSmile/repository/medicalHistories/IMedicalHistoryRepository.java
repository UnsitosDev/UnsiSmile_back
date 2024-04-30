package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.MedicalHistoryModel;

public interface IMedicalHistoryRepository extends JpaRepository<MedicalHistoryModel, Long> {
    
}