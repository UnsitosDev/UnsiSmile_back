package edu.mx.unsis.unsiSmile.dtos.request.addresses;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HousingRequest {
    @NotBlank(message = ResponseMessages.ID_HOUSING_BLANK)
    @Size(min = 2, max = 2, message = ResponseMessages.ID_HOUSING_SIZE)
    private String idHousing;

    @NotBlank(message = ResponseMessages.CATEGORY_BLANK)
    private String category;
}