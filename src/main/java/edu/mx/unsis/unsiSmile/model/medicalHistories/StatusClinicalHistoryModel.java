package edu.mx.unsis.unsiSmile.model.medicalHistories;

import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "status_clinical_history")
public class StatusClinicalHistoryModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status_clinical_history")
    private Long idStatusClinicalHistory;

    @Column(name = "status", nullable = false, length = 100)
    private String status;

    @Column(name = "message", columnDefinition = "LONGTEXT")
    private String message;

    @OneToOne
    @JoinColumn(name = "fk_patient_clinical_history", nullable = false)
    private PatientClinicalHistoryModel patientClinicalHistory;
}
