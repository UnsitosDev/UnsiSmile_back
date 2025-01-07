package edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram;

import java.util.List;

import edu.mx.unsis.unsiSmile.model.FormSectionModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @JoinColumn(name = "fk_form_section", referencedColumnName = "id_form_section", unique = true)
    private FormSectionModel formSection;

    @OneToMany(mappedBy = "odontogram", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<ToothConditionAssignmentModel> toothConditionAssignments;

    @OneToMany(mappedBy = "odontogram", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<ToothfaceConditionsAssignmentModel> toothFaceConditionsAssignments;
}
