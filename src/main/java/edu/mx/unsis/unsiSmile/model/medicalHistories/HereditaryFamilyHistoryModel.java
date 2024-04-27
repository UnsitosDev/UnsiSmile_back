package edu.mx.unsis.unsiSmile.model.medicalHistories;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "HereditaryFamilyHistory")
public class HereditaryFamilyHistoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_family_history")
    private Long idFamilyHistory;

    @ManyToOne
    @JoinColumn(name = "fk_question")
    private HereditaryFamilyHistoryQuestionModel question;

    @Enumerated(EnumType.STRING)
    @Column(name = "main_response", nullable = false)
    private Response mainResponse;

    @Column(name = "response_detail", columnDefinition = "TEXT")
    private String responseDetail;

    public enum Response {
        SI,
        NO
    }
}
