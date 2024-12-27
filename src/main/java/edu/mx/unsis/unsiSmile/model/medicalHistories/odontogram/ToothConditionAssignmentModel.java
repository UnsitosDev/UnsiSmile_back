package edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import edu.mx.unsis.unsiSmile.model.utils.ToothConditionAssignmentId;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tooth_condition_assignments")
@IdClass(ToothConditionAssignmentId.class)
public class ToothConditionAssignmentModel extends AuditModel {

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
    @JoinColumn(name = "odontogram_id", nullable = false)
    private OdontogramModel odontogram;

}