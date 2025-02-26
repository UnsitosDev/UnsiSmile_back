package edu.mx.unsis.unsiSmile.dtos.response.addresses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocalityResponse {
    private Long idLocality;
    private String name;
    private String postalCode;
    private MunicipalityResponse municipality;
}