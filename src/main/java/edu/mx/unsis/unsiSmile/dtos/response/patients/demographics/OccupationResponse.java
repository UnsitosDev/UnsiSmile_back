package edu.mx.unsis.unsiSmile.dtos.response.patients.demographics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OccupationResponse {
    private Long idOccupation;
    private String occupation;
}