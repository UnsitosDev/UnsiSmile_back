package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToothFaceConditionRequest {
    private Long toothConditionId;
    private String toothId;
    private String toothFaceId;
    private LocalDate creationDate;
}
