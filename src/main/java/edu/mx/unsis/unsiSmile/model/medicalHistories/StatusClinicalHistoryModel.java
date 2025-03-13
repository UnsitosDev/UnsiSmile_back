package edu.mx.unsis.unsiSmile.model.medicalHistories;

import edu.mx.unsis.unsiSmile.model.FormSectionModel;
import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "status_clinical_history")
public class StatusClinicalHistoryModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status_clinical_history")
    private Long idStatusClinicalHistory;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 100)
    private ClinicalHistoryStatus status;

    @Column(name = "message", columnDefinition = "LONGTEXT")
    private String message;

    @ManyToOne
    @JoinColumn(name = "fk_patient_clinical_history", nullable = false)
    private PatientClinicalHistoryModel patientClinicalHistory;

    @ManyToOne
    @JoinColumn(name = "fk_form_section")
    private FormSectionModel formSection;
}
