package edu.mx.unsis.unsiSmile.model.medicalrecords.odontogram;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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