package edu.mx.unsis.unsiSmile.model.medicalrecords.odontogram;

import edu.mx.unsis.unsiSmile.model.medicalrecords.DentalProphylaxisModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothConditionModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import edu.mx.unsis.unsiSmile.model.utils.ProphylaxisToothConditionAssignmentId;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "prophylaxis_tooth_condition_assignments")
@IdClass(ProphylaxisToothConditionAssignmentId.class)
public class ProphylaxisToothConditionAssignmentModel extends AuditModel {

    @Id
    @ManyToOne
    @JoinColumn(name = "tooth_id", nullable = false)
    private ToothModel tooth;

    @Id
    @ManyToOne
    @JoinColumn(name = "tooth_condition_id", nullable = false)
    private ToothConditionModel toothCondition;

    @Id
    @ManyToOne
    @JoinColumn(name = "dental_prophylaxis_id", nullable = false)
    private DentalProphylaxisModel dentalProphylaxis;
}