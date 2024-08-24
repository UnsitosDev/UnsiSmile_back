package edu.mx.unsis.unsiSmile.model.utils;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class ToothConditionAssignmentId implements Serializable {
    private String tooth;
    private Long toothCondition;
    private Long odontogram;
}
