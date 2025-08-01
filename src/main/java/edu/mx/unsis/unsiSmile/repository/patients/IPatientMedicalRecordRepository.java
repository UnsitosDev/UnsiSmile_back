package edu.mx.unsis.unsiSmile.repository.patients;

import edu.mx.unsis.unsiSmile.model.patients.PatientMedicalRecordModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPatientMedicalRecordRepository extends JpaRepository<PatientMedicalRecordModel, Long> {
    PatientMedicalRecordModel findFirstByPatient_IdPatientAndMedicalRecordCatalog_MedicalRecordNameOrderByCreatedAtDesc(
            String patientId, String medicalRecordName);

    Optional<PatientMedicalRecordModel> findByPatient_IdPatientAndIdPatientMedicalRecord(
            String patientId, Long idPatientMedicalRecord);

    Optional<PatientMedicalRecordModel> findByPatient_IdPatientAndMedicalRecordCatalog_IdMedicalRecordCatalog(
            String patientId, Long idMedicalRecordCatalog);

    @Query("SELECT pmr FROM PatientMedicalRecordModel pmr " +
            "WHERE pmr.patient.idPatient = :idPatient " +
            "AND pmr.medicalRecordCatalog.idMedicalRecordCatalog = :idMedicalRecordCatalog " +
            "AND pmr.idPatientMedicalRecord NOT IN :usedIds")
    List<PatientMedicalRecordModel> findAvailableByPatientAndCatalogExcludingIds(
            @Param("idPatient") String idPatient,
            @Param("idMedicalRecordCatalog") Long idMedicalRecordCatalog,
            @Param("usedIds") List<Long> usedIds);

    Page<PatientMedicalRecordModel> findByPatient_IdPatient(String idPatient, Pageable pageable);
}