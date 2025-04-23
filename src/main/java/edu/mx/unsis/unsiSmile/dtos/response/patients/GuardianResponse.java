package edu.mx.unsis.unsiSmile.dtos.response.patients;

import edu.mx.unsis.unsiSmile.dtos.response.CatalogOptionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.PersonResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GuardianResponse {
    private Long idGuardian;
    private CatalogOptionResponse parentalStatus;
    private String doctorName; 
    private PersonResponse person;
}