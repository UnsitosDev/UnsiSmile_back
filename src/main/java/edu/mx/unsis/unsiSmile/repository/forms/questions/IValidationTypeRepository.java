package edu.mx.unsis.unsiSmile.repository.forms.questions;

import edu.mx.unsis.unsiSmile.model.forms.questions.ValidationTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IValidationTypeRepository extends JpaRepository<ValidationTypeModel, Long> {
}