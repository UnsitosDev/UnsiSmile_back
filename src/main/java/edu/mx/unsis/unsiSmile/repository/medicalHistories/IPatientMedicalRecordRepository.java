package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.PatientMedicalRecordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPatientMedicalRecordRepository extends JpaRepository<PatientMedicalRecordModel, Long> {
    PatientMedicalRecordModel findFirstByPatient_IdPatientAndMedicalRecordCatalog_MedicalRecordNameOrderByCreatedAtDesc(
            String patientId, String medicalRecordName);

    Optional<PatientMedicalRecordModel> findByPatient_IdPatientAndIdPatientMedicalRecord(
            String patientId, Long idPatientMedicalRecord);
}