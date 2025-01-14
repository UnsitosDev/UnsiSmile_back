package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPatientClinicalHistoryRepository extends JpaRepository<PatientClinicalHistoryModel, Long> {

    @Query("SELECT pch FROM PatientClinicalHistoryModel pch where pch.patient.idPatient = :patientId")
    List<PatientClinicalHistoryModel> findAllByPatientId(@Param("patientId") String patientId);

    Optional<PatientClinicalHistoryModel> findByPatient_IdPatientAndClinicalHistoryCatalog_IdClinicalHistoryCatalog(
            String patientId, Long clinicalHistoryCatalogId);
}