package edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.teeth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaceResponse {
    private String idFace;
    private List<ConditionResponse> conditions;
}