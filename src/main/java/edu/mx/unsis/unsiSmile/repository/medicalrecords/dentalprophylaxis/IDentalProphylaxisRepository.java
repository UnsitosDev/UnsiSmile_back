package edu.mx.unsis.unsiSmile.repository.medicalrecords.dentalprophylaxis;

import edu.mx.unsis.unsiSmile.model.medicalrecords.dentalprophylaxis.DentalProphylaxisModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.dentalprophylaxis.ProphylaxisToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.dentalprophylaxis.ProphylaxisToothfaceConditionsAssignmentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDentalProphylaxisRepository extends JpaRepository<DentalProphylaxisModel, Long> {

    @Query("SELECT ptca FROM ProphylaxisToothConditionAssignmentModel ptca WHERE ptca.dentalProphylaxis.idDentalProphylaxis = :prophylaxisId")
    List<ProphylaxisToothConditionAssignmentModel> findToothConditionAssignmentsByProphylaxisId(Long prophylaxisId);

    @Query("SELECT ptfca FROM ProphylaxisToothfaceConditionsAssignmentModel ptfca WHERE ptfca.dentalProphylaxis.idDentalProphylaxis = :prophylaxisId")
    List<ProphylaxisToothfaceConditionsAssignmentModel> findToothFaceConditionsAssignmentByProphylaxisId(Long prophylaxisId);

    @Query("SELECT dp FROM DentalProphylaxisModel dp WHERE dp.patientMedicalRecord.idPatientMedicalRecord = :patientMedicalRecordId ORDER BY dp.createdAt DESC")
    Page<DentalProphylaxisModel> findByPatientMedicalRecordId(@Param("patientMedicalRecordId") Long patientMedicalRecordId, Pageable pageable);

    @Query("SELECT dp FROM DentalProphylaxisModel dp WHERE dp.patientMedicalRecord.patient.idPatient = :patientId ORDER BY dp.createdAt DESC")
    Page<DentalProphylaxisModel> findByPatientId(String patientId, Pageable pageable);
}