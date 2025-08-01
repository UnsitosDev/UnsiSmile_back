package edu.mx.unsis.unsiSmile.model.treatments;

import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "treatment_detail_teeth")
public class TreatmentDetailToothModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detail_tooth")
    private Long idDetailTooth;

    @ManyToOne
    @JoinColumn(name = "fk_treatment_detail", referencedColumnName = "id_treatment_detail", nullable = false)
    private TreatmentDetailModel treatmentDetail;

    @ManyToOne
    @JoinColumn(name = "fk_tooth", referencedColumnName = "id_tooth", nullable = false)
    private ToothModel tooth;

    @ManyToOne
    @JoinColumn(name = "fk_execution_review", referencedColumnName = "id_execution_review")
    private ExecutionReviewModel status;

    @Builder.Default
    @Column(name = "start_date")
    private LocalDateTime startDate = LocalDateTime.now();

    @Column(name = "end_date")
    private LocalDateTime endDate;
}