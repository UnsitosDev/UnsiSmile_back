package edu.mx.unsis.unsiSmile.model.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import edu.mx.unsis.unsiSmile.model.students.StudentGroupModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "treatment_details")
public class TreatmentDetailModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_treatment_detail")
    private Long idTreatmentDetail;

    @ManyToOne
    @JoinColumn(name = "fk_patient_clinical_history", referencedColumnName = "id_patient_clinical_history", nullable = false)
    private PatientClinicalHistoryModel patientClinicalHistory;

    @ManyToOne
    @JoinColumn(name = "fk_treatment", referencedColumnName = "id_treatment", nullable = false)
    private TreatmentModel treatment;

    @ManyToOne
    @JoinColumn(name = "fk_treatment_scope", referencedColumnName = "id_scope")
    private TreatmentScopeModel treatmentScope;

    @Column(name = "treatment_date", nullable = false)
    private LocalDateTime treatmentDate;

    @ManyToOne
    @JoinColumn(name = "fk_student_group", referencedColumnName = "id_student_groups", nullable = false)
    private StudentGroupModel studentGroup;

    @ManyToOne
    @JoinColumn(name = "fk_professor", referencedColumnName = "id_professor")
    private ProfessorModel professor;

    @Column(name = "status", length = 50)
    private String status;
}