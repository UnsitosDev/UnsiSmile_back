package edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis;

import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothConditionModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import edu.mx.unsis.unsiSmile.model.utils.FluorosisToothConditionAssignmentId;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "fluorosis_tooth_condition_assignments")
@IdClass(FluorosisToothConditionAssignmentId.class)
public class FluorosisToothConditionAssignmentModel extends AuditModel {

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
    @JoinColumn(name = "fluorosis_id", nullable = false)
    private FluorosisModel fluorosis;
}