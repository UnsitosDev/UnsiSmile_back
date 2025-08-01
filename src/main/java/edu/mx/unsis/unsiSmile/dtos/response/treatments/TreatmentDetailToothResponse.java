package edu.mx.unsis.unsiSmile.dtos.response.treatments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentDetailToothResponse {

    private Long idDetailTooth;
    private String idTooth;
    private LocalDate endDate;
    private String status;
    private Boolean isRecent;
}