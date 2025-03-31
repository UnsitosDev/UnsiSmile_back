package edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram;

import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.ToothFaceConditionModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.ToothFaceModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.ToothModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import edu.mx.unsis.unsiSmile.model.utils.ToothFaceConditionAssignmentId;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "toothface_conditions_assignments")
@IdClass(ToothFaceConditionAssignmentId.class)
public class ToothfaceConditionsAssignmentModel extends AuditModel {
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
    @JoinColumn(name = "odontogram_id")
    private OdontogramModel odontogram;
}
