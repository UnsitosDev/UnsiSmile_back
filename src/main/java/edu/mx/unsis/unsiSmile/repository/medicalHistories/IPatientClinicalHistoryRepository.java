package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPatientClinicalHistoryRepository extends JpaRepository<PatientClinicalHistoryModel, Long> {

    @Query("SELECT pch FROM PatientClinicalHistoryModel pch where pch.patient.idPatient = :patientId")
    List<PatientClinicalHistoryModel> findAllByPatientId(@Param("patientId") Long patientId);
}
