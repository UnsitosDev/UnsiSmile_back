package edu.mx.unsis.unsiSmile.model.medicalrecords.dentalprophylaxis;

import edu.mx.unsis.unsiSmile.model.patients.PatientMedicalRecordModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sohi")
public class SOHIModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sohi")
    private Long idSohi;

    @OneToOne
    @JoinColumn(name = "fk_patient_medical_record", referencedColumnName = "id_patient_medical_record")
    private PatientMedicalRecordModel patientMedicalRecord;

    @OneToMany(mappedBy = "sohi", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<SOHIToothCodeModel> toothCodes;
}