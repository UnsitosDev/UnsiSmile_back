package edu.mx.unsis.unsiSmile.model.medicalrecords.dentalprophylaxis;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProphylaxisToothFaceConditionAssignmentId implements Serializable {
    private String toothFace;
    private Long toothFaceCondition;
    private String tooth;
    private Long dentalProphylaxis;
}