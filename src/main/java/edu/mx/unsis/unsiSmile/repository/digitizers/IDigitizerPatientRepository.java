package edu.mx.unsis.unsiSmile.repository.digitizers;

import edu.mx.unsis.unsiSmile.model.digitizers.DigitizerPatientModel;
import edu.mx.unsis.unsiSmile.model.digitizers.MedicalRecordDigitizerModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDigitizerPatientRepository extends JpaRepository<DigitizerPatientModel, Long> {

    Optional<DigitizerPatientModel> findByIdDigitizerPatient(Long idDigitizerPatient);

    Optional<DigitizerPatientModel> findByPatient_IdPatientAndDigitizer_IdMedicalRecordDigitizerAndStatusKey(String patientId, Long digitizerId, String statusKey);

    Page<DigitizerPatientModel> findByDigitizer_User_Username(String enrollment, Pageable pageable);

    @Query("SELECT dp FROM DigitizerPatientModel dp " +
            "WHERE dp.digitizer = :digitizer " +
            "AND dp.statusKey = 'A'")
    Page<DigitizerPatientModel> findActiveByDigitizer(@Param("digitizer") MedicalRecordDigitizerModel digitizer, Pageable pageable);

    @Query("SELECT dp FROM DigitizerPatientModel dp WHERE dp.patient.idPatient = :patientId AND " +
            "dp.statusKey = 'A' AND dp.digitizer.statusKey = 'A'")
    Page<DigitizerPatientModel> findByPatientId(@Param("patientId") String patientId, Pageable pageable);

    List<DigitizerPatientModel> findByIdDigitizerPatientAndStatusKey(Long digitizerId, String statusKey);
}