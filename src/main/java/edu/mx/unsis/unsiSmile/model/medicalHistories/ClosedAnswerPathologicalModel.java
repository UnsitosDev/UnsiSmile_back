package edu.mx.unsis.unsiSmile.model.medicalHistories;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ClosedAnswersNonPathological")
public class ClosedAnswerPathologicalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_closed_answer_non_pathological")
    private Long idClosedAnswerNonPathological;

    @ManyToOne
    @JoinColumn(name = "fk_closed_questions_id", referencedColumnName = "id_closed_question")
    private ClosedQuestionsPathologicalAntecedentsModel closedQuestion;

    @ManyToOne
    @JoinColumn(name = "fk_medical_histories", referencedColumnName = "id_medical_history")
    private MedicalHistoryModel medicalHistory;

    @Column(name = "answer", columnDefinition = "TEXT")
    private String answer;
}
