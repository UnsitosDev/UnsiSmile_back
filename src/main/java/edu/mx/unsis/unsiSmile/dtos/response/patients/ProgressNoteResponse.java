package edu.mx.unsis.unsiSmile.dtos.response.patients;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProgressNoteResponse {
    private String idProgressNote;
    private String bloodPressure;
    private BigDecimal temperature;
    private Integer heartRate;
    private Integer respirationRate;
    private BigDecimal oxygenSaturation;
    private String diagnosis;
    private String prognosis;
    private String treatment;
    private String indications;
    private String student;
    private String professor;
    private List<ProgressNoteFileResponse> files;
    private PatientRes patient;
    private String creationDate;
}