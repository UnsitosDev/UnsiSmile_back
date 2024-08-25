package edu.mx.unsis.unsiSmile.model.utils;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToothConditionAssignmentId implements Serializable {
    private String tooth;
    private Long toothCondition;
    private Long odontogram;
}
