package edu.mx.unsis.unsiSmile.dtos.response.students;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecordDigitizerResponse {
    private Long idMedicalRecordDigitizer;
    private String digitizerName;
    private String username;
    private LocalDate startDate;
    private Boolean status;
}