package edu.mx.unsis.unsiSmile.dtos.response.patients;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EthnicGroupResponse {
    private Long idEthnicGroup;
    private String ethnicGroup;
}