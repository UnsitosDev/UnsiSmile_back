package edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis;

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
@Table(name = "fluorosis")
public class FluorosisModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fluorosis")
    private Long idFluorosis;

    @OneToOne
    @JoinColumn(name = "fk_patient_medical_record", referencedColumnName = "id_patient_medical_record")
    private PatientMedicalRecordModel patientMedicalRecord;

    @OneToMany(mappedBy = "fluorosis", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<FluorosisToothConditionAssignmentModel> toothConditionAssignments;

    @OneToMany(mappedBy = "fluorosis", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<FluorosisToothfaceConditionsAssignmentModel> toothFaceConditionsAssignments;
}