package edu.mx.unsis.unsiSmile.dtos.response.students;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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