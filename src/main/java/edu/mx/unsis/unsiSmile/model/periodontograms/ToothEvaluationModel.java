package edu.mx.unsis.unsiSmile.model.periodontograms;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tooth_evaluations")
public class ToothEvaluationModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idToothEvaluation;

    @ManyToOne
    @JoinColumn(name = "fk_periodontogram", nullable = false)
    private PeriodontogramModel periodontogram;

    @Column(nullable = false, length = 3)
    private String idTooth;

    private Integer mobility;

    @OneToMany(mappedBy = "toothEvaluation", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private Set<SurfaceEvaluationModel> surfaceEvaluations;
}