package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPatientClinicalHistoryRepository extends JpaRepository<PatientClinicalHistoryModel, Long> {

    @Query("SELECT pch FROM PatientClinicalHistoryModel pch " +
            "WHERE pch.patient.idPatient = :patientId " +
            "AND pch.clinicalHistoryCatalog.clinicalHistoryName = :medicalRecordName " +
            "ORDER BY pch.createdAt DESC")
    PatientClinicalHistoryModel findTopByPatientIdAndRecordName(@Param("patientId") String patientId,
                                                                @Param("medicalRecordName") String medicalRecordName);

    Optional<PatientClinicalHistoryModel> findByPatient_IdPatientAndIdPatientClinicalHistory(
            String patientId, Long idPatientClinicalHistory);
}