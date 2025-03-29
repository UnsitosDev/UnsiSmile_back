package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.medicalHistories.DentalProphylaxisModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ProphylaxisToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ProphylaxisToothfaceConditionsAssignmentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDentalProphylaxisRepository extends JpaRepository<DentalProphylaxisModel, Long> {

    @Query("SELECT ptca FROM ProphylaxisToothConditionAssignmentModel ptca WHERE ptca.dentalProphylaxis.idDentalProphylaxis = :prophylaxisId")
    List<ProphylaxisToothConditionAssignmentModel> findToothConditionAssignmentsByProphylaxisId(Long prophylaxisId);

    @Query("SELECT ptfca FROM ProphylaxisToothfaceConditionsAssignmentModel ptfca WHERE ptfca.dentalProphylaxis.idDentalProphylaxis = :prophylaxisId")
    List<ProphylaxisToothfaceConditionsAssignmentModel> findToothFaceConditionsAssignmentByProphylaxisId(Long prophylaxisId);

    @Query("SELECT dp FROM DentalProphylaxisModel dp WHERE dp.formSection.idFormSection = :formSectionId AND dp.patient.idPatient = :patientId")
    Optional<DentalProphylaxisModel> findByFormSectionIdAndPatientId(@Param("formSectionId") Long formSectionId, @Param("patientId") String patientId);

    Page<DentalProphylaxisModel> findByPatient_IdPatientOrderByCreatedAtDesc(String patientId, Pageable pageable);
}