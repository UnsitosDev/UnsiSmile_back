package edu.mx.unsis.unsiSmile.model.periodontograms;

import java.time.LocalDateTime;
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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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