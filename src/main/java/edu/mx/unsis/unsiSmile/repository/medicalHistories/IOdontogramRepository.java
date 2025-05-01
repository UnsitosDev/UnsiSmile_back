package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.OdontogramModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ToothfaceConditionsAssignmentModel;

@Repository
public interface IOdontogramRepository extends JpaRepository<OdontogramModel, Long> {
    // Consulta para obtener las asignaciones de condiciones dentales y de caras del diente de un odontograma específico
    @Query("SELECT o, tca, tfc FROM OdontogramModel o " +
            "LEFT JOIN o.toothConditionAssignments tca " +
            "LEFT JOIN o.toothFaceConditionsAssignments tfc " +
            "WHERE o.idOdontogram = :odontogramId")
    List<Object[]> findOdontogramDetailsById(Long odontogramId);

    @Query("SELECT tca FROM ToothConditionAssignmentModel tca WHERE tca.odontogram.idOdontogram = :odontogramId")
    List<ToothConditionAssignmentModel> findToothConditionAssignmentsByOdontogramId(Long odontogramId);

    @Query("SELECT tfca FROM ToothfaceConditionsAssignmentModel tfca WHERE tfca.odontogram.idOdontogram = :odontogramId")
    List<ToothfaceConditionsAssignmentModel> findToothFaceConditionsAssignmentByOdontogramId(Long odontogramId);

    @Query("SELECT o FROM OdontogramModel o WHERE o.patient.id = :patientId ORDER BY o.createdAt DESC limit 1")
    Optional<OdontogramModel> findLatestOdontogramByPatientId(@Param("patientId") String patientId);

    @Query("SELECT o FROM OdontogramModel o WHERE o.formSection.id = :formSectionId AND o.patient.id = :patientId")
    Optional<OdontogramModel> findByFormSectionIdAndPatientId(@Param("formSectionId") Long formSectionId, @Param("patientId") String patientId);

    Optional<OdontogramModel> findFirstByPatient_IdPatientOrderByCreatedAtDesc(String patientId);
}
