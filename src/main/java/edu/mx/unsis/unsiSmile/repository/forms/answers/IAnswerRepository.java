package edu.mx.unsis.unsiSmile.repository.forms.answers;

import edu.mx.unsis.unsiSmile.model.forms.answers.AnswerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface IAnswerRepository extends JpaRepository<AnswerModel, Long> {

    @Query("SELECT a FROM AnswerModel a WHERE a.patientMedicalRecordModel.idPatientMedicalRecord = :patientMedicalRecordId")
    List<AnswerModel> findAllByPatientMedicalRecordId(@Param("patientMedicalRecordId") Long patientMedicalRecordId);

    @Query("SELECT a FROM AnswerModel a WHERE a.questionModel.idQuestion IN :questionIds AND " +
            "a.patientModel.idPatient = :patientId")
    List<AnswerModel> findAllByPatientIdAndQuestionIds(@Param("questionIds") Set<Long> questionIds,
                                                       @Param("patientId") String patientId);

    @Query("SELECT a FROM AnswerModel a WHERE a.questionModel.idQuestion  IN :questionIds AND " +
            "a.patientModel.idPatient = :patientId AND a.patientMedicalRecordModel.idPatientMedicalRecord = :patientMedicalRecordId")
    List<AnswerModel> findAllByPatientMedicalRecordIdAndPatientIdAndQuestionIds(@Param("questionIds") Set<Long> questionIds,
                                                                                @Param("patientId") String patientId,
                                                                                @Param("patientMedicalRecordId") Long patientMedicalRecordId);

    Optional<AnswerModel> findByQuestionModelIdQuestionAndPatientModel_IdPatient(Long id, String idPatient);

    Optional<AnswerModel> findByQuestionModel_IdQuestion(Long idQuestion);

    @Query("SELECT a FROM AnswerModel a WHERE a.questionModel.idQuestion  IN :questionsIds")
    List<AnswerModel> findByQuestion_Ids(@Param("questionsIds") Set<Long> questionsIds);
}
