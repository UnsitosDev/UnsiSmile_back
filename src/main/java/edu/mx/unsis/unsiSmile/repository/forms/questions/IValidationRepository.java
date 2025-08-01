package edu.mx.unsis.unsiSmile.repository.forms.questions;

import edu.mx.unsis.unsiSmile.model.forms.questions.ValidationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IValidationRepository extends JpaRepository<ValidationModel, Long> {
}