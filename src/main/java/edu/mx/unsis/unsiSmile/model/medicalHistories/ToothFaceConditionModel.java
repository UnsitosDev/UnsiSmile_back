package edu.mx.unsis.unsiSmile.model.medicalHistories;

import edu.mx.unsis.unsiSmile.model.utils.ToothFaceConditionId;
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
@Table(name = "toothface_conditions")
@IdClass(ToothFaceConditionId.class)
public class ToothFaceConditionModel {

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

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;
}