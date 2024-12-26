package edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import edu.mx.unsis.unsiSmile.model.utils.ToothFaceConditionId;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "toothface_conditions")
@IdClass(ToothFaceConditionId.class)
public class ToothFaceConditionModel extends AuditModel {

    @Id
    @ManyToOne
    @JoinColumn(name = "tooth_face_id")
    private ToothFaceModel toothFace;

    @Id
    @ManyToOne
    @JoinColumn(name = "tooth_condition_id")
    private ToothConditionModel toothCondition;

    @Id
    @ManyToOne
    @JoinColumn(name = "tooth_id")
    private ToothModel tooth;

    @Id
    @ManyToOne
    @JoinColumn(name = "odontogram_id")
    private OdontogramModel odontogram;

}