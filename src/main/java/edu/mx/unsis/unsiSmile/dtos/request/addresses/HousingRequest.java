package edu.mx.unsis.unsiSmile.dtos.request.addresses;

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
    @NotBlank(message = "Housing ID cannot be blank")
    @Size(min = 2, max = 2, message = "Housing ID must be 2 characters long")
    private String idHousing;

    @NotBlank(message = "Housing category cannot be blank")
    private String category;
}