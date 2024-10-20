package edu.mx.unsis.unsiSmile.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatalogOptionRequest {

    @NotNull(message = "The Option Name cannot be null.")
    private String optionName;

    @NotNull(message = "The Id Catalog cannot be null.")
    private Long idCatalog;
}
