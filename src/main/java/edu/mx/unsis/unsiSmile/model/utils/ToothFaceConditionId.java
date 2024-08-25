package edu.mx.unsis.unsiSmile.model.utils;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToothFaceConditionId implements Serializable {
    private String toothFace;
    private Long toothCondition;
    private String tooth;
    private Long odontogram;
}