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
@Table(name = "dean_index")
public class DeanIndexModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dean_index")
    private Long idDeanIndex;

    @OneToOne
    @JoinColumn(name = "fk_patient_medical_record", referencedColumnName = "id_patient_medical_record")
    private PatientMedicalRecordModel patientMedicalRecord;

    @OneToMany(mappedBy = "deanIndex", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<DeanIndexToothCodeModel> toothCodes;
}