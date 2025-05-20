package edu.mx.unsis.unsiSmile.model.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FluorosisToothConditionAssignmentId implements Serializable {
    private String tooth;
    private Long toothCondition;
    private Long fluorosis;
}