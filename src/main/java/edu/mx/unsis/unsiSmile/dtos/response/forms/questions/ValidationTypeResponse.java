package edu.mx.unsis.unsiSmile.dtos.response.forms.questions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationTypeResponse {
    private Long idValidationType;
    private String validationCode;
}