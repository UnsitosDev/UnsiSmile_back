package edu.mx.unsis.unsiSmile.dtos.response.addresses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {
    private Long idAddress;
    private String streetNumber;
    private String interiorNumber;
    private HousingResponse housing;
    private StreetResponse street;
}