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

    @NotNull(message = "The field group can't be null")
    @NotBlank(message = "The field group can't be blank")
    private String groupName;
    @NotNull(message = "The field group can't be null")
    private CareerModel career;
}
