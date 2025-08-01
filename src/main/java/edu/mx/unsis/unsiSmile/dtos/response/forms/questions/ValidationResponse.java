package edu.mx.unsis.unsiSmile.dtos.response.forms.questions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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