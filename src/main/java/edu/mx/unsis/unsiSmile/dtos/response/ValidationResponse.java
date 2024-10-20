package edu.mx.unsis.unsiSmile.dtos.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationResponse {

    private Long idValidation;

    private String validationValue;

    private String validationMessage;

    private ValidationTypeResponse validationType;
}
