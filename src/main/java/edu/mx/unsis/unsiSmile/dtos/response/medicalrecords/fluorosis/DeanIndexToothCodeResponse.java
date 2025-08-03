package edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.fluorosis;

import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.fluorosis.EnumDeanIndexToothId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeanIndexToothCodeResponse {
    private Long id;
    private Long idPatientMedicalRecord;
    private List<DeanIndexToothCodeResp> teeth;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeanIndexToothCodeResp {
        private Long id;
        private EnumDeanIndexToothId idTooth;
        private Integer code;
    }
}