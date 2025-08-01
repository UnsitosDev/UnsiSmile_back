package edu.mx.unsis.unsiSmile.model.medicalrecords.dentalprophylaxis;

import edu.mx.unsis.unsiSmile.model.treatments.TreatmentDetailModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "dental_prophylaxis")
public class DentalProphylaxisModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dental_prophylaxis")
    private Long idDentalProphylaxis;

    @ManyToOne
    @JoinColumn(name = "fk_treatment_detail", referencedColumnName = "id_treatment_detail")
    private TreatmentDetailModel treatmentDetail;

    @OneToMany(mappedBy = "dentalProphylaxis", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<ProphylaxisToothConditionAssignmentModel> toothConditionAssignments;

    @OneToMany(mappedBy = "dentalProphylaxis", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<ProphylaxisToothfaceConditionsAssignmentModel> toothFaceConditionsAssignments;

    @Column(name = "percentage", nullable = false, precision = 5, scale = 2)
    private BigDecimal percentage;
}