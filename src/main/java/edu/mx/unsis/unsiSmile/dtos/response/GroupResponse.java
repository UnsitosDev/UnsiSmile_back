package edu.mx.unsis.unsiSmile.dtos.response;

import edu.mx.unsis.unsiSmile.model.CareerModel;
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
