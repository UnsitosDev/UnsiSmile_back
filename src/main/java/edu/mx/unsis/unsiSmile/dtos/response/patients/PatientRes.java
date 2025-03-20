package edu.mx.unsis.unsiSmile.dtos.response.patients;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientRes {
    private String idPatient;
    private String name;
    private LocalDate birthDate;
    private Long age;
    private String origin;
    private Long medicalRecordNumber;
    private LocalDate creationDate;
    private Boolean isMinor;
    private String guardian;
    private String gender;
}
