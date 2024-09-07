package edu.mx.unsis.unsiSmile.model;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "answers")
public class AnswerModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_answer")
    private Integer idAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_patient_clinical_history", nullable = false)
    private PatientClinicalHistoryModel patientClinicalHistoryModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_question", nullable = false)
    private QuestionModel questionModel;

    @Column(name = "answer_boolean")
    private Boolean answerBoolean;

    @Column(name = "answer_numeric", precision = 10, scale = 2)
    private BigDecimal answerNumeric;

    @Column(name = "answer_text")
    private String answerText;

    @Column(name = "answer_date")
    private LocalDateTime answerDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_option")
    private CatalogOptionModel catalogOptionModel;

    @Enumerated(EnumType.STRING)
    @Column(name = "answer_type", nullable = false)
    private AnswerType answerType;

    public enum AnswerType {
        BOOLEAN,
        NUMERIC,
        TEXT,
        CATALOG
    }
}
