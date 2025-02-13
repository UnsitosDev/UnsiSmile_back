package edu.mx.unsis.unsiSmile.dtos.response.students;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SemesterResponse {
    private Long idSemester;
    private String semesterName;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private CycleResponse cycle;
}
