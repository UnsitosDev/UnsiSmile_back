package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DentalProphylaxisResponse {
    private Long idDentalProphylaxis;
    private LocalDate creationDate;
    private List<ToothResponse> teethProphylaxis;
}
