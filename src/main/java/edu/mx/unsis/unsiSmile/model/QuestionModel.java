package edu.mx.unsis.unsiSmile.model;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "questions")
public class QuestionModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_question")
    private Long idQuestion;

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_form_section", nullable = false)
    private FormSectionModel formSectionModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_answer_type", nullable = false)
    private AnswerTypeModel answerTypeModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_catalog")
    private CatalogModel catalogModel;
}
