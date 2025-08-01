package edu.mx.unsis.unsiSmile.repository.forms.questions;

import edu.mx.unsis.unsiSmile.model.forms.questions.QuestionValidationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IQuestionValidationRepository extends JpaRepository<QuestionValidationModel, Long> {

    @Query("SELECT qv FROM QuestionValidationModel qv WHERE qv.questionModel.idQuestion = :questionId")
    List<QuestionValidationModel> findAllByQuestionId(@Param("questionId") Long questionId);
}