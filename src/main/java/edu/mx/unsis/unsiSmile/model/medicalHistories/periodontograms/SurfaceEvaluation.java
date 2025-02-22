package edu.mx.unsis.unsiSmile.model.medicalHistories.periodontograms;

import java.util.List;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "surface_evaluation")
public class SurfaceEvaluation extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSurfaceEvaluation;

    @ManyToOne
    @JoinColumn(name = "fk_tooth_evaluation", nullable = false)
    private ToothEvaluation toothEvaluation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Surface surface;

    @OneToMany(mappedBy = "surfaceEvaluation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurfaceMeasurement> surfaceMeasurements;

    public enum Surface {
        VESTIBULAR, LINGUAL, MESIAL, DISTAL
    }
}