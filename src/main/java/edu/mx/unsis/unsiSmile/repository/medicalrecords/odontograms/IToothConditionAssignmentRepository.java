package edu.mx.unsis.unsiSmile.repository.medicalrecords.odontograms;

import edu.mx.unsis.unsiSmile.model.medicalrecords.odontogram.ToothConditionAssignmentId;
import edu.mx.unsis.unsiSmile.model.medicalrecords.odontogram.ToothConditionAssignmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IToothConditionAssignmentRepository extends JpaRepository<ToothConditionAssignmentModel, ToothConditionAssignmentId> {

    @Query("SELECT t FROM ToothConditionAssignmentModel t WHERE t.odontogram.idOdontogram = :odontogramId")
    List<ToothConditionAssignmentModel> findByOdontogramId(@Param("odontogramId") Long odontogramId);
}