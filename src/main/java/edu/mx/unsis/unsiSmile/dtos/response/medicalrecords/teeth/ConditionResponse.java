package edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.teeth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConditionResponse {
    private Long idCondition;
    private String condition;
    private String description;
}