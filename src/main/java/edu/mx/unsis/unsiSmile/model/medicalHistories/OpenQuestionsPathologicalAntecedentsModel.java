package edu.mx.unsis.unsiSmile.model.medicalHistories;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "OpenQuestionsPathologicalAntecedents")
public class OpenQuestionsPathologicalAntecedentsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_open_question")
    private Long idOpenQuestion;

    @Column(name = "question", length = 300, nullable = false)
    private String question;
}

