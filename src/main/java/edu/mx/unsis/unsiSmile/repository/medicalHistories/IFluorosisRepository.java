package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.medicalHistories.fluorosis.FluorosisModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.fluorosis.FluorosisToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.fluorosis.FluorosisToothfaceConditionsAssignmentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFluorosisRepository extends JpaRepository<FluorosisModel, Long> {

    @Query("SELECT ftca FROM FluorosisToothConditionAssignmentModel ftca WHERE ftca.fluorosis.idFluorosis = :fluorosisId")
    List<FluorosisToothConditionAssignmentModel> findToothConditionAssignmentsByFluorosisId(Long fluorosisId);

    @Query("SELECT ftfca FROM FluorosisToothfaceConditionsAssignmentModel ftfca WHERE ftfca.fluorosis.idFluorosis = :fluorosisId")
    List<FluorosisToothfaceConditionsAssignmentModel> findToothFaceConditionsAssignmentByFluorosisId(Long fluorosisId);

    @Query("SELECT f FROM FluorosisModel f WHERE f.formSection.idFormSection = :formSectionId AND f.patient.idPatient = :patientId")
    Optional<FluorosisModel> findByFormSectionIdAndPatientId(@Param("formSectionId") Long formSectionId, @Param("patientId") String patientId);

    Page<FluorosisModel> findByPatient_IdPatientOrderByCreatedAtDesc(String patientId, Pageable pageable);
}