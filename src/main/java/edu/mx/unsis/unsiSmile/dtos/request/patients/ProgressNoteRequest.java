package edu.mx.unsis.unsiSmile.dtos.request.patients;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProgressNoteRequest {

    @NotBlank(message = "El ID del paciente es obligatorio")
    private String patientId;

    @NotBlank(message = "La presión arterial es obligatoria")
    @Size(max = 10, message = "La presión arterial no puede superar los 10 caracteres")
    private String bloodPressure;

    @NotNull(message = "La temperatura es obligatoria")
    @Digits(integer = 3, fraction = 2, message = "La temperatura debe ser un número con máximo 3 enteros y 2 decimales")
    private BigDecimal temperature;

    @NotNull(message = "La frecuencia cardíaca es obligatoria")
    @Positive(message = "La frecuencia cardíaca debe ser un número positivo")
    private Integer heartRate;

    @NotNull(message = "La frecuencia respiratoria es obligatoria")
    @Positive(message = "La frecuencia respiratoria debe ser un número positivo")
    private Integer respiratoryRate;

    @NotNull(message = "La saturación de oxígeno es obligatoria")
    @Digits(integer = 3, fraction = 2, message = "La saturación de oxígeno debe ser un número con máximo 3 enteros y 2 decimales")
    private BigDecimal oxygenSaturation;

    @NotBlank(message = "El diagnóstico es obligatorio")
    private String diagnosis;

    @NotBlank(message = "El pronóstico es obligatorio")
    private String prognosis;

    @NotBlank(message = "El tratamiento es obligatorio")
    private String treatment;

    private String clinicalStatus;

    private String indications;

    @NotBlank(message = "El ID del profesor del área clínica es obligatorio")
    private Long professorClinicalAreaId;
}
