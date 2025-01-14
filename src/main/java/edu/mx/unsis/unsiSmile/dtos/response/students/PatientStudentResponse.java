package edu.mx.unsis.unsiSmile.dtos.response.students;

import edu.mx.unsis.unsiSmile.mappers.students.StudentRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientStudentResponse {
    private Long idStudentPatient;
    private String patientId;
    private StudentRes student;
}