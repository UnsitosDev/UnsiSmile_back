package edu.mx.unsis.unsiSmile.repository;

import edu.mx.unsis.unsiSmile.model.QuestionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IQuestionRepository extends JpaRepository<QuestionModel, Long> {

    @Query("SELECT q FROM QuestionModel q WHERE q.formSectionModel.idFormSection = :sectionId ORDER BY q.order")
    List<QuestionModel> findAllByFormSectionId(@Param("sectionId") String sectionId);
}
