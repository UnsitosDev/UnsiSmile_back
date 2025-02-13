package edu.mx.unsis.unsiSmile.dtos.response.students;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CycleResponse {
    private Long idCycle;
    private String cycleName;
}
