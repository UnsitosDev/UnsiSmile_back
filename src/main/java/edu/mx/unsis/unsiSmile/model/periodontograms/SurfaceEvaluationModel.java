package edu.mx.unsis.unsiSmile.model.periodontograms;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "surface_evaluations")
public class SurfaceEvaluationModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSurfaceEvaluation;

    @ManyToOne
    @JoinColumn(name = "fk_tooth_evaluation", nullable = false)
    private ToothEvaluationModel toothEvaluation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Surface surface;

    @OneToMany(mappedBy = "surfaceEvaluation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SurfaceMeasurementModel> surfaceMeasurements;

    public enum Surface {
        VESTIBULAR, PALATINO, LINGUAL, VESTIBULAR_INFERIOR
    }
}