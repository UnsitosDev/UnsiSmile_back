package edu.mx.unsis.unsiSmile.dtos.response.professors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorClinicalAreaResponse {
    private Long idProfessorClinicalArea;
    private String professorName;
    private ClinicalAreaResponse clinicalArea;
    
}
