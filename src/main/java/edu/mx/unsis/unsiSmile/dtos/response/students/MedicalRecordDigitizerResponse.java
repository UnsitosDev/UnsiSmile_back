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
    private String studentFullName;
    private String idStudent;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean status;
}