package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPatientClinicalHistoryRepository extends JpaRepository<PatientClinicalHistoryModel, Long> {
    PatientClinicalHistoryModel findFirstByPatient_IdPatientAndClinicalHistoryCatalog_ClinicalHistoryNameOrderByCreatedAtDesc(
            String patientId, String medicalRecordName);

    Optional<PatientClinicalHistoryModel> findByPatient_IdPatientAndIdPatientClinicalHistory(
            String patientId, Long idPatientClinicalHistory);
}