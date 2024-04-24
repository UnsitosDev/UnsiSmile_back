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
public class CycleRequest {
    private Long idCycle;
    @NotNull(message = "The field cycle can't be null")
    @NotBlank(message = "The field cycle can't be blank")
    private String cycleName;
    @NotNull(message = "The field cycle can't be null")
    @NotBlank(message = "The field cycle can't be blank")
    private Boolean status;

}
