package edu.mx.unsis.unsiSmile.dtos.request.forms.sections;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormSectionRequest {

    @NotNull(message = "The Form Name cannot be null.")
    private String formName;

    @NotNull(message = "The requiresReview cannot be null.")
    private Boolean requiresReview;

    private String idParentSection;
}
