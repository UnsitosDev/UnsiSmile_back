package edu.mx.unsis.unsiSmile.dtos.response.addresses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateResponse {
    private String idState;
    private String name;
}