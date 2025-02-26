package edu.mx.unsis.unsiSmile.model.professors;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "professor_clinical_areas")
public class ProfessorClinicalAreaModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_professor_clinical_area", nullable = false)
    private Long idProfessorClinicalArea;

    @ManyToOne
    @JoinColumn(name = "fk_clinical_area", nullable = false)
    private ClinicalAreaModel clinicalArea;

    @ManyToOne
    @JoinColumn(name = "fk_professor", nullable = false)
    private ProfessorModel professor;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;
}