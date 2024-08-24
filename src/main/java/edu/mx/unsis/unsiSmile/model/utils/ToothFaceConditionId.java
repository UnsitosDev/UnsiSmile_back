package edu.mx.unsis.unsiSmile.model.utils;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class ToothFaceConditionId implements Serializable {
    private String toothFace;
    private Long toothCondition;
    private String tooth;
    private Long odontogram;
}