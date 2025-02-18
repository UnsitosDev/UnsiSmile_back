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
public class ClinicalAreaRequest {
    private Long idClinicalArea;

    @NotNull(message = "The clinical area field cannot be null")
    private String clinicalArea;
}
