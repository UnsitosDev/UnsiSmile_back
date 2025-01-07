package edu.mx.unsis.unsiSmile.model.medicalHistories.odontogram;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import edu.mx.unsis.unsiSmile.model.utils.ToothConditionAssignmentId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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