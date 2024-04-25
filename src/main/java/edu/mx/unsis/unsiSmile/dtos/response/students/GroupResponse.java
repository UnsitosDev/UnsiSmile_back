package edu.mx.unsis.unsiSmile.dtos.response.students;

import edu.mx.unsis.unsiSmile.model.students.CareerModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponse {
    private Long idGroup;
    private String groupName;
    private CareerModel career;
}
