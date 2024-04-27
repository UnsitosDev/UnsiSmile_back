package edu.mx.unsis.unsiSmile.dtos.response.students;

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
    private GroupResponse group;
    private CycleResponse cycle;
}
