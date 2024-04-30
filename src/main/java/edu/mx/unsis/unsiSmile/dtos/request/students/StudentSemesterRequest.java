package edu.mx.unsis.unsiSmile.dtos.request.students;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentSemesterRequest {
    private Long idStudentSemester;

    @NotNull(message = "The field student can not be null")
    private StudentRequest student;
    @NotNull (message = "The field seemester can not be null")
    private SemesterRequest semester;
}
