package edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram;

import edu.mx.unsis.unsiSmile.model.medicalHistories.DentalProphylaxisModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import edu.mx.unsis.unsiSmile.model.utils.ProphylaxisToothFaceConditionAssignmentId;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "prophylaxis_toothface_conditions_assignments")
@IdClass(ProphylaxisToothFaceConditionAssignmentId.class)
public class ProphylaxisToothfaceConditionsAssignmentModel extends AuditModel {

    @Id
    @ManyToOne
    @JoinColumn(name = "tooth_face_id")
    private ToothFaceModel toothFace;

    @Id
    @ManyToOne
    @JoinColumn(name = "toothface_condition_id")
    private ToothFaceConditionModel toothFaceCondition;

    @Id
    @ManyToOne
    @JoinColumn(name = "tooth_id")
    private ToothModel tooth;

    @Id
    @ManyToOne
    @JoinColumn(name = "dental_prophylaxis_id")
    private DentalProphylaxisModel dentalProphylaxis;
}