package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacialExamRequest {
    
    private Long idFacialExam;
    
    private String distinguishingFeatures;

    @NotNull(message = "The field facialProfile can't be null")
    private FacialProfileRequest facialProfile;
    
    @NotNull(message = "The field facialFront can't be null")
    private FacialFrontRequest facialFront;

    @NotNull(message = "Patientid can´t be null")
    private Long patientId;
}
