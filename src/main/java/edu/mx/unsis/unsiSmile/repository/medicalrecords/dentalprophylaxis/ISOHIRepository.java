package edu.mx.unsis.unsiSmile.repository.medicalrecords.dentalprophylaxis;

import edu.mx.unsis.unsiSmile.model.medicalrecords.dentalprophylaxis.SOHIModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISOHIRepository extends JpaRepository<SOHIModel, Long> {

    Optional<SOHIModel> findByPatientMedicalRecord_IdPatientMedicalRecord(Long patientMedicalRecordId);
}