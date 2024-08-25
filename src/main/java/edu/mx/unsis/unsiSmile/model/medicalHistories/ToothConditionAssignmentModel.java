package edu.mx.unsis.unsiSmile.model.medicalHistories;

import edu.mx.unsis.unsiSmile.model.utils.ToothConditionAssignmentId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tooth_condition_assignment")
@IdClass(ToothConditionAssignmentId.class)
public class ToothConditionAssignmentModel {

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

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;
}