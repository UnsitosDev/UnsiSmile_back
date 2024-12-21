package edu.mx.unsis.unsiSmile.repository;

import edu.mx.unsis.unsiSmile.model.AnswerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface IAnswerRepository extends JpaRepository<AnswerModel, Long> {

    @Query("SELECT a FROM AnswerModel a WHERE a.patientClinicalHistoryModel.idPatientClinicalHistory = :patientClinicalHistoryId")
    List<AnswerModel> findAllByPatientClinicalHistoryId(@Param("patientClinicalHistoryId") Long patientClinicalHistoryId);

    @Query("SELECT a FROM AnswerModel a WHERE a.questionModel.idQuestion IN :questionIds AND " +
            "a.patientModel.idPatient = :patientId")
    List<AnswerModel> findAllByPatientClinicalHistoryId(@Param("questionIds") Set<Long> questionIds,
                                                        @Param("patientId") UUID patientId);

    Optional<AnswerModel> findByQuestionModelIdQuestionAndPatientModel_IdPatient(Long id, UUID idPatient);
}
