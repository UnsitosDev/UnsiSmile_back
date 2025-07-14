package edu.mx.unsis.unsiSmile.model.students;

import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "digitizer_patients")
public class DigitizerPatientModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_digitizer_patient")
    private Long idDigitizerPatient;

    @ManyToOne
    @JoinColumn(name = "fk_patient")
    private PatientModel patient;

    @ManyToOne
    @JoinColumn(name = "fk_medical_record_digitizer")
    private MedicalRecordDigitizerModel digitizer;
}