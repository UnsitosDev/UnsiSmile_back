package edu.mx.unsis.unsiSmile.model;

import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "patient_clinical_histories")
public class PatientClinicalHistoryModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patient_clinical_history")
    private Long idPatientClinicalHistory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_medical_record_catalog")
    private MedicalRecordCatalogModel medicalRecordCatalog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_patient")
    private PatientModel patient;

    @Column(name = "appointment_date")
    private LocalDateTime appointmentDate;
}