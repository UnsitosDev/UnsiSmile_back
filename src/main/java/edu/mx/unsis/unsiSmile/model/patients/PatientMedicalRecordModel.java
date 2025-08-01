package edu.mx.unsis.unsiSmile.model.patients;

import edu.mx.unsis.unsiSmile.model.MedicalRecordCatalogModel;
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
@Table(name = "patient_medical_records")
public class PatientMedicalRecordModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patient_medical_record")
    private Long idPatientMedicalRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_medical_record_catalog")
    private MedicalRecordCatalogModel medicalRecordCatalog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_patient")
    private PatientModel patient;

    @Column(name = "appointment_date")
    private LocalDateTime appointmentDate;
}