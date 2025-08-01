package edu.mx.unsis.unsiSmile.mappers;

import edu.mx.unsis.unsiSmile.dtos.request.CatalogOptionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.CatalogOptionResponse;
import edu.mx.unsis.unsiSmile.model.forms.catalogs.CatalogModel;
import edu.mx.unsis.unsiSmile.model.forms.catalogs.CatalogOptionModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CatalogOptionMapper implements BaseMapper<CatalogOptionResponse, CatalogOptionRequest, CatalogOptionModel>{

    @Override
    public CatalogOptionModel toEntity(CatalogOptionRequest dto) {
        return CatalogOptionModel.builder()
                .idCatalogOption(dto.getIdCatalogOption())
                .optionName(dto.getOptionName())
                .catalogModel(CatalogModel.builder()
                        .idCatalog(dto.getIdCatalog())
                        .build())
                .build();
    }

    @Override
    public CatalogOptionResponse toDto(CatalogOptionModel entity) {
        return CatalogOptionResponse.builder()
                .idCatalogOption(entity.getIdCatalogOption())
                .optionName(entity.getOptionName())
                .build();
    }

    @Override
    public List<CatalogOptionResponse> toDtos(List<CatalogOptionModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(CatalogOptionRequest request, CatalogOptionModel entity) {}
}
