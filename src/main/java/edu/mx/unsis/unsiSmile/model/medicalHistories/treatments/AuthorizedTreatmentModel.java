package edu.mx.unsis.unsiSmile.model.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.model.professors.ProfessorClinicalAreaModel;
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
@Table(name = "authorized_treatments")
public class AuthorizedTreatmentModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_authorized_treatment")
    private Long idAuthorizedTreatment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_treatment_detail", referencedColumnName = "id_treatment_detail", nullable = false)
    private TreatmentDetailModel treatmentDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_professor_clinical_area", referencedColumnName = "id_professor_clinical_area", nullable = false)
    private ProfessorClinicalAreaModel professorClinicalArea;

    @Column(name = "comment", length = 255)
    private String comment;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "authorized_at")
    private LocalDateTime authorizedAt;
}