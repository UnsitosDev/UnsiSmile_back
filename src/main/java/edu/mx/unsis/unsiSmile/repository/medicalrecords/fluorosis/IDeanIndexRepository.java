package edu.mx.unsis.unsiSmile.repository.medicalrecords.fluorosis;

import edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis.DeanIndexModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDeanIndexRepository extends JpaRepository<DeanIndexModel, Long> {

    Optional<DeanIndexModel> findByPatientMedicalRecord_IdPatientMedicalRecord(Long patientMedicalRecordId);
}