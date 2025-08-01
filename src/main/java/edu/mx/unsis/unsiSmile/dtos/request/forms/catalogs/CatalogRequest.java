package edu.mx.unsis.unsiSmile.dtos.request.forms.catalogs;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatalogRequest {

    @NotNull(message = "The Catalog Name cannot be null")
    private String catalogName;
}
