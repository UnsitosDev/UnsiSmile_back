package edu.mx.unsis.unsiSmile.dtos.request.medicalrecords;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToothConditionAssignmentRequest {

    private Long toothConditionId;
    private String toothId;
    private LocalDate creationDate;
}