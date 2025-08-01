package edu.mx.unsis.unsiSmile.model.forms.questions;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "question_validations")
public class QuestionValidationModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_question_validation")
    private Long idQuestionValidation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_question", nullable = false)
    private QuestionModel questionModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_validation")
    private ValidationModel validationModel;
}