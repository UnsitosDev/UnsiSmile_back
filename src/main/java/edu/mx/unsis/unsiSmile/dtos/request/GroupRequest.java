package edu.mx.unsis.unsiSmile.dtos.request;

import edu.mx.unsis.unsiSmile.model.CareerModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "The field career can't be null")
    @NotBlank(message = "The field career can't be blank")
    private String groupName;
    @NotNull(message = "The field career can't be null")
    private CareerModel career;
}
