package edu.mx.unsis.unsiSmile.model.periodontograms;

import java.util.Set;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "tooth_evaluation")
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
    private Set<SurfaceEvaluationModel> surfaceEvaluations;
}