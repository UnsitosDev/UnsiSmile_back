package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacialExamResponse {
    private Long idFacialExam;
    private String distinguishingFeatures;
    private FacialProfileResponse facialProfile;
    private FacialFrontResponse facialFront;
}
