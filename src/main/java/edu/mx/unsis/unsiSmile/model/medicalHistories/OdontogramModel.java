package edu.mx.unsis.unsiSmile.model.medicalHistories;

import edu.mx.unsis.unsiSmile.model.FormSectionModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "odontograms")
public class OdontogramModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_odontogram")
    private Long idOdontogram;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "fk_patient", referencedColumnName = "id_patient")
    private PatientModel patient;

    @ManyToOne
    @JoinColumn(name = "fk_form_section", referencedColumnName = "id_form_section")
    private FormSectionModel formSection;

    @OneToMany(mappedBy = "odontogram", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<ToothConditionAssignmentModel> toothConditionAssignments;

    @OneToMany(mappedBy = "odontogram", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<ToothFaceConditionModel> toothFaceConditions;
}
