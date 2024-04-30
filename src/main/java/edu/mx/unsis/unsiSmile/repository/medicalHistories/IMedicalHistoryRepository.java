package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.MedicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;

public interface IMedicalHistoryRepository extends JpaRepository<MedicalHistoryModel, Long> {
    List<MedicalHistoryModel> findByPatient(PatientModel patient);
}
