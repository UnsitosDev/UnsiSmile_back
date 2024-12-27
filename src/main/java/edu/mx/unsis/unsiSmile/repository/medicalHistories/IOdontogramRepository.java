package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ToothFaceConditionModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ToothfaceConditionsAssignmentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.OdontogramModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}
