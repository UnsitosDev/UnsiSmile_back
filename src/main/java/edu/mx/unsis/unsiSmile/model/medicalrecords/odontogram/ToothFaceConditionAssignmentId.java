package edu.mx.unsis.unsiSmile.model.medicalrecords.odontogram;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToothFaceConditionAssignmentId implements Serializable {
    private String toothFace;
    private Long toothFaceCondition;
    private String tooth;
    private Long odontogram;
}