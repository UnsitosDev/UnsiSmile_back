package edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.fluorosis;

import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.teeth.ToothResponse;
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
public class FluorosisResponse {
    private Long idFluorosis;
    private LocalDate creationDate;
    private List<ToothResponse> teethFluorosis;
}