package edu.mx.unsis.unsiSmile.repository.medicalrecords.odontograms;

import edu.mx.unsis.unsiSmile.model.medicalrecords.odontogram.OdontogramModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.odontogram.ToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.odontogram.ToothfaceConditionsAssignmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
       
    //obtener todos los odontogramas por id de paciente
    @Query("SELECT o FROM OdontogramModel o " +
           "WHERE o.patient.idPatient = :patientId " +
           "ORDER BY o.createdAt DESC")
    List<OdontogramModel> findAllOdontogramsByPatientIdOrderByCreatedAtDesc(@Param("patientId") String patientId);
    
    // Obtener el último odontograma por ID de paciente
    @Query("SELECT o FROM OdontogramModel o " +
           "WHERE o.patient.idPatient = :patientId " +
           "ORDER BY o.createdAt DESC")
    Optional<OdontogramModel> findLastOdontogramByPatientId(@Param("patientId") String patientId);
}