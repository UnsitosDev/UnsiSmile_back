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

    List<OdontogramModel> findByTreatment_IdTreatment(Long idTreatment);

    @Query("SELECT o FROM OdontogramModel o WHERE o.treatment.idTreatment = :idTreatment")
    List<OdontogramModel> findOdontogramByTreatmentId(@Param("idTreatment") Long idTreatment);

    //get last odontogram by treatment
    @Query("SELECT o FROM OdontogramModel o WHERE o.treatment.idTreatment = :idTreatment ORDER BY o.createdAt DESC limit 1")
    Optional<OdontogramModel> findLastOdontogramByTreatmentId(@Param("idTreatment") Long idTreatment);

}
