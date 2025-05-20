package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

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
    private List<ToothResponse> teeth;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ToothResponse {
        private Long id;
        private String idTooth;
        private Integer code;
    }
}
