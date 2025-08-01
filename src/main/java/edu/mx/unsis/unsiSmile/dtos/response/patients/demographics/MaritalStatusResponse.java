package edu.mx.unsis.unsiSmile.dtos.response.patients.demographics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaritalStatusResponse {
    private Long idMaritalStatus;
    private String maritalStatus;
}