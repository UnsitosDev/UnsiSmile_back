package edu.mx.unsis.unsiSmile.model.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.model.medicalHistories.ReviewStatus;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorClinicalAreaModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "execution_reviews")
public class ExecutionReviewModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_execution_review")
    private Long idExecutionReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_treatment_detail", referencedColumnName = "id_treatment_detail", nullable = false)
    private TreatmentDetailModel treatmentDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_professor_clinical_area", referencedColumnName = "id_professor_clinical_area", nullable = false)
    private ProfessorClinicalAreaModel professorClinicalArea;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReviewStatus status; // IN_REVIEW, REJECTED, FINISHED

    @Column(name = "comment", length = 255)
    private String comment;
}
