package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VitalSignsRequest {

    private Long idVitalSigns;

    @NotNull(message = "Weight cannot be null")
    private Float weight;

    @NotNull(message = "Height cannot be null")
    private Float height;

    @NotNull(message = "Temperature cannot be null")
    private Float temperature;

    @NotNull(message = "Heart rate cannot be null")
    private Float heartRate;

    @NotNull(message = "Respiratory rate cannot be null")
    private Float respiratoryRate;

    @NotNull(message = "Blood pressure cannot be null")
    private Float bloodPressure;

    @NotNull(message = "Oxygen saturation cannot be null")
    private Float oxygenSaturation;

    @NotNull(message = "Glucose cannot be null")
    private Float glucose;

    @NotNull(message = "Pulse cannot be null")
    private Float pulse;

    @NotNull(message = "Patientid can´t be null")
    private Long patientId;
}