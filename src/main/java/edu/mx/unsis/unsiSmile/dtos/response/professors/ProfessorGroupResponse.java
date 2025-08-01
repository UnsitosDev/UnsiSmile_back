package edu.mx.unsis.unsiSmile.dtos.response.professors;

import edu.mx.unsis.unsiSmile.dtos.response.students.GroupResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorGroupResponse {
    private Long idProfessorGroup;
    private String professorName;
    private GroupResponse group;
}