package edu.mx.unsis.unsiSmile.model;

import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    private Long idAnswer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_patient_medical_record")
    private PatientMedicalRecordModel patientMedicalRecordModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_question", nullable = false)
    private QuestionModel questionModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_patient")
    private PatientModel patientModel;

    @Column(name = "answer_boolean")
    private Boolean answerBoolean;

    @Column(name = "answer_numeric", precision = 10, scale = 2)
    private BigDecimal answerNumeric;

    @Column(name = "answer_text")
    private String answerText;

    @Column(name = "answer_date")
    private LocalDate answerDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_option")
    private CatalogOptionModel catalogOptionModel;

    @Column(name = "is_file")
    private Boolean isFile;
}
