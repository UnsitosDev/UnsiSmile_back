package edu.mx.unsis.unsiSmile.dtos.response.professors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClinicalAreaResponse {
    private Long idClinicalArea;
    private String clinicalArea;
    private Page<ProfessorResponse> professors;
}