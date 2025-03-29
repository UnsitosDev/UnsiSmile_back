package edu.mx.unsis.unsiSmile.model.medicalHistories;

import edu.mx.unsis.unsiSmile.model.FormSectionModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ProphylaxisToothConditionAssignmentModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram.ProphylaxisToothfaceConditionsAssignmentModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
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
@Table(name = "dental_prophylaxis", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"fk_patient", "fk_form_section"})
})
public class DentalProphylaxisModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dental_prophylaxis")
    private Long idDentalProphylaxis;

    @ManyToOne
    @JoinColumn(name = "fk_patient", referencedColumnName = "id_patient")
    private PatientModel patient;

    @ManyToOne
    @JoinColumn(name = "fk_form_section", referencedColumnName = "id_form_section")
    private FormSectionModel formSection;

    @OneToMany(mappedBy = "dentalProphylaxis", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<ProphylaxisToothConditionAssignmentModel> toothConditionAssignments;

    @OneToMany(mappedBy = "dentalProphylaxis", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<ProphylaxisToothfaceConditionsAssignmentModel> toothFaceConditionsAssignments;
}
