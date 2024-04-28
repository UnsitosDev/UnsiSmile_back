package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.PatientModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.MedicalHistoryModel;

public interface IMedicalHistoryRepository extends JpaRepository<MedicalHistoryModel, Long> {
    List<MedicalHistoryModel> findByPatient(PatientModel patient);
}
