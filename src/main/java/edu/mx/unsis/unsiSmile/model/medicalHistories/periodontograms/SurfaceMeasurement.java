package edu.mx.unsis.unsiSmile.model.medicalHistories.periodontograms;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "surface_measurement")
public class SurfaceMeasurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSurfaceMeasurement;

    @ManyToOne
    @JoinColumn(name = "fk_surface_evaluation", nullable = false)
    private SurfaceEvaluation surfaceEvaluation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ToothPosition toothPosition;

    private Double pocketDepth;
    private Double lesionLevel;
    private Boolean plaque;
    private Boolean bleeding;
    private Boolean calculus;

    public enum ToothPosition {
        MESIAL, CENTRAL, DISTAL
    }
}