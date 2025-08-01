package edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.components;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormComponentResponse {
    private Long idFormComponent;
    private String description;
}