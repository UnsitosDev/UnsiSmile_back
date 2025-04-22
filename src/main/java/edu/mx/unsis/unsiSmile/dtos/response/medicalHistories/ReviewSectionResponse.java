package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ReviewSectionResponse {
    private Long idReviewStatus;
    private String status;
    private String message;
}