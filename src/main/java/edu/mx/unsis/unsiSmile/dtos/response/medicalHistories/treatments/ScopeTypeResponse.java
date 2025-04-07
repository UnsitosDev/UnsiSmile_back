package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScopeTypeResponse {
    private Long idScopeType;
    private String name;
}