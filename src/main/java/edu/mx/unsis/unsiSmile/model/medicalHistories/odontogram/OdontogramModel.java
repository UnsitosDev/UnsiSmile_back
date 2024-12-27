package edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram;

import edu.mx.unsis.unsiSmile.model.FormSectionModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "odontograms")
public class OdontogramModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_odontogram")
    private Long idOdontogram;

    @ManyToOne
    @JoinColumn(name = "fk_patient", referencedColumnName = "id_patient")
    private PatientModel patient;

    @ManyToOne
    @JoinColumn(name = "fk_form_section", referencedColumnName = "id_form_section")
    private FormSectionModel formSection;

    @OneToMany(mappedBy = "odontogram", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<ToothConditionAssignmentModel> toothConditionAssignments;

    @OneToMany(mappedBy = "odontogram", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<ToothfaceConditionsAssignmentModel> toothFaceConditionsAssignments;
}
