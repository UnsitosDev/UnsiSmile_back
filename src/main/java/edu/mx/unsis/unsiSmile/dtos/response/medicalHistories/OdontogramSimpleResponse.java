package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OdontogramSimpleResponse {
    private Long idOdontogram;
    private Long idTreatment;
    private LocalDate creationDate;
}
