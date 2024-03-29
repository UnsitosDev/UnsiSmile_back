package edu.mx.unsis.unsiSmile.dtos.request;

import edu.mx.unsis.unsiSmile.model.CareerModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequest {
    private Long idGroup;
    private String groupName;
    private CareerModel career;
}
