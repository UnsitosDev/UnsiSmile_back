package edu.mx.unsis.unsiSmile.dtos.request.medicalHistories;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToothRequest {

    @NotNull(message = "The tooth ID cannot be null.")
    private Long idTooth;

    @NotNull(message = "At least one condition must be associated with the tooth.")
    @Size(min = 1, message = "At least one condition must be associated with the tooth.")
    private List<ConditionRequest> conditions;
    @NotNull(message = "At least one facial view must be associated with the tooth.")
    @Size(min = 1, message = "At least one facial view must be associated with the tooth.")
    private List<FaceRequest> faces;
}