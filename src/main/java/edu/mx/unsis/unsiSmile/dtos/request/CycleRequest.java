package edu.mx.unsis.unsiSmile.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CycleRequest {
    private Long idCycle;
    private String cycleName;
    private Boolean status;

}
