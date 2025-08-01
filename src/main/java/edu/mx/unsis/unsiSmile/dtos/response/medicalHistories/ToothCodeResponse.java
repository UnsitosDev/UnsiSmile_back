package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.dentalprophylaxis.EnumToothId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToothCodeResponse {
    private Long id;
    private Long idTreatment;
    private List<ToothResp> teeth;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ToothResp {
        private Long id;
        private EnumToothId idTooth;
        private Integer code;
    }
}
