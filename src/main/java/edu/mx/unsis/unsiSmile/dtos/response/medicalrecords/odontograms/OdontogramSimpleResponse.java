package edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.odontograms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OdontogramSimpleResponse {
    private Long idOdontogram;
    private String idPatient;
    private LocalDate creationDate;
}