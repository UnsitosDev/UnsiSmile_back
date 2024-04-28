package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VitalSignsResponse {
    private Long idVitalSigns;
    private Float weight;
    private Float height;
    private Float temperature;
    private Float heartRate;
    private Float respiratoryRate;
    private Float bloodPressure;
    private Float oxygenSaturation;
    private Float glucose;
    private Float pulse;
}