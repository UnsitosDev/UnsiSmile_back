package edu.mx.unsis.unsiSmile.dtos.request.professors;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorClinicalAreaRequest {
    
    private Long idProfessorClinicalArea;

    @NotNull(message = "The idClinicalArea field cannot be null")
    private Long idClinicalArea;

    @NotNull(message = "The idProfessor field cannot be null")
    private String idProfessor;
}