package edu.mx.unsis.unsiSmile.dtos.request;

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
public class GenderRequest {

    private Long idGender;
    @NotNull(message = "The field career can't be null")
    @NotBlank(message = "The field career can't be blank")
    private String gender;
}
