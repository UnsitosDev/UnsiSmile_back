package edu.mx.unsis.unsiSmile.model.periodontograms;

import edu.mx.unsis.unsiSmile.model.forms.sections.FormSectionModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "periodontograms", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "fk_patient", "fk_form_section" })
})
public class PeriodontogramModel extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_periodontogram")
    private Long idPeriodontogram;

    private Double plaqueIndex;
    private Double bleedingIndex;

    @Column(nullable = false)
    private LocalDateTime evaluationDate;

    @Lob
    private String notes;

    @ManyToOne
    @JoinColumn(name = "fk_form_section", referencedColumnName = "id_form_section")
    private FormSectionModel formSection;

    @ManyToOne
    @JoinColumn(name = "fk_patient", referencedColumnName = "id_patient")
    private PatientModel patient;

    @OneToMany(mappedBy = "periodontogram", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ToothEvaluationModel> toothEvaluations;
}