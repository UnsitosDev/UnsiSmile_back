package edu.mx.unsis.unsiSmile.model.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.ToothModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "treatment_detail_teeth")
public class TreatmentDetailToothModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detail_tooth")
    private Long idDetailTooth;

    @ManyToOne
    @JoinColumn(name = "fk_treatment_detail", referencedColumnName = "id_treatment_detail", nullable = false)
    private TreatmentDetailModel treatmentDetail;

    @ManyToOne
    @JoinColumn(name = "fk_tooth", referencedColumnName = "id_tooth", nullable = false)
    private ToothModel tooth;
}