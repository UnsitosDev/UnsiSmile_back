package edu.mx.unsis.unsiSmile.dtos.response.students;

import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentPatientResponse {
    private Long idStudentPatient;
    private PatientResponse patient;
    private StudentResponse student;
}