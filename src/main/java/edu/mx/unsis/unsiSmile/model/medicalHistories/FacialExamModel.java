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
@Table(name = "facial_exam")
public class FacialExamModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_facial_exam")
    private Long idFacialExam;

    @Column(name = "distinguishing_features", columnDefinition = "TEXT")
    private String distinguishingFeatures;

    @ManyToOne
    @JoinColumn(name = "fk_profile", referencedColumnName = "id_facial_profile", nullable = false)
    private FacialProfileModel facialProfile;

    @ManyToOne
    @JoinColumn(name = "fk_front", referencedColumnName = "id_facial_front", nullable = false)
    private FacialFrontModel facialFront;

}
