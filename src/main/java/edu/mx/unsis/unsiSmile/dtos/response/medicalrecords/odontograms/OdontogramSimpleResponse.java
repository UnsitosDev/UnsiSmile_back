package edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.odontograms;

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
    private String idPatient;
    private LocalDate creationDate;
}
