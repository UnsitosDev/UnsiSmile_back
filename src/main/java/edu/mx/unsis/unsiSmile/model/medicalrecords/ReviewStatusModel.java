package edu.mx.unsis.unsiSmile.model.medicalrecords;

import edu.mx.unsis.unsiSmile.model.FormSectionModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientMedicalRecordModel;
import edu.mx.unsis.unsiSmile.model.enums.ReviewStatus;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorClinicalAreaModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "review_status")
public class ReviewStatusModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_review_status")
    private Long idReviewStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 100)
    private ReviewStatus status;

    @Column(name = "message", columnDefinition = "LONGTEXT")
    private String message;

    @ManyToOne
    @JoinColumn(name = "fk_patient_medical_record", nullable = false)
    private PatientMedicalRecordModel patientMedicalRecord;

    @ManyToOne
    @JoinColumn(name = "fk_form_section")
    private FormSectionModel formSection;

    @ManyToOne
    @JoinColumn(name = "fk_professor_clinical_area", nullable = false)
    private ProfessorClinicalAreaModel professorClinicalArea;
}