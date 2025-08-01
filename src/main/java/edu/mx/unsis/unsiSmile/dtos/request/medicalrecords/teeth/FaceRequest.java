package edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.teeth;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FaceRequest {
    @NotNull(message = "The face ID cannot be null.")
    private Long idFace;
    private List<ToothFaceConditionRequest> conditions;
}
