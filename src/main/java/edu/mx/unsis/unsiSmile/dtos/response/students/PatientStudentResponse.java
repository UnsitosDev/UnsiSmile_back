package edu.mx.unsis.unsiSmile.dtos.response.students;

import edu.mx.unsis.unsiSmile.mappers.students.StudentRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientStudentResponse {
    private Long idStudentPatient;
    private UUID patientId;
    private StudentRes student;
}