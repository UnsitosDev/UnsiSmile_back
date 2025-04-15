package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreatmentDetailToothResponse {

    private Long idDetailTooth;
    private Long idTreatmentDetail;
    private List<String> idTeeth;
}