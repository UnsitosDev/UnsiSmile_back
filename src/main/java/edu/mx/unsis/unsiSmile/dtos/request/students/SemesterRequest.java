package edu.mx.unsis.unsiSmile.dtos.request.students;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SemesterRequest {
    private Long idSemester;

    @NotNull(message = "The field SemesterName can't be null")
    private String semesterName;

    @NotNull(message = "The field fechaInicio can't be null")
    private LocalDate fechaInicio;

    @NotNull(message = "The field fechaFin can't be null")
    private LocalDate fechaFin;

    @NotNull(message = "The field Cycle can't be null")
    private CycleRequest cycle;
}
