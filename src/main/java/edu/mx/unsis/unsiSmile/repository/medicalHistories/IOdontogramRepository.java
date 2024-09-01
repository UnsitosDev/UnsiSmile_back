package edu.mx.unsis.unsiSmile.repository.medicalHistories;

import edu.mx.unsis.unsiSmile.model.medicalHistories.ToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ToothFaceConditionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.medicalHistories.OdontogramModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOdontogramRepository extends JpaRepository<OdontogramModel, Long> {
    // Consulta para obtener las asignaciones de condiciones dentales y de caras del diente de un odontograma específico
    @Query("SELECT o, tca, tfc FROM OdontogramModel o " +
            "LEFT JOIN o.toothConditionAssignments tca " +
            "LEFT JOIN o.toothFaceConditions tfc " +
            "WHERE o.idOdontogram = :odontogramId")
    List<Object[]> findOdontogramDetailsById(Long odontogramId);

    @Query("SELECT tca FROM ToothConditionAssignmentModel tca WHERE tca.odontogram.idOdontogram = :odontogramId")
    List<ToothConditionAssignmentModel> findToothConditionAssignmentsByOdontogramId(Long odontogramId);

    @Query("SELECT tfc FROM ToothFaceConditionModel tfc WHERE tfc.odontogram.idOdontogram = :odontogramId")
    List<ToothFaceConditionModel> findToothFaceConditionsByOdontogramId(Long odontogramId);

}
