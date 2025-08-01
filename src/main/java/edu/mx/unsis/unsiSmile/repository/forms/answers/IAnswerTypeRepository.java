package edu.mx.unsis.unsiSmile.repository.forms.answers;

import edu.mx.unsis.unsiSmile.model.forms.answers.AnswerTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAnswerTypeRepository extends JpaRepository<AnswerTypeModel, Long> {
}