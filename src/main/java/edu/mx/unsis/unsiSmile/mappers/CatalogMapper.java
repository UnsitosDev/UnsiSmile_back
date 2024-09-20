package edu.mx.unsis.unsiSmile.mappers;

import edu.mx.unsis.unsiSmile.dtos.request.CatalogRequest;
import edu.mx.unsis.unsiSmile.dtos.response.CatalogResponse;
import edu.mx.unsis.unsiSmile.model.CatalogModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CatalogMapper implements BaseMapper<CatalogResponse, CatalogRequest, CatalogModel>{
    @Override
    public CatalogModel toEntity(CatalogRequest dto) {
        return CatalogModel.builder()
                .catalogName(dto.getCatalogName())
                .build();
    }

    @Override
    public CatalogResponse toDto(CatalogModel entity) {
        return CatalogResponse.builder()
                .idCatalog(entity.getIdCatalog())
                .catalogName(entity.getCatalogName())
                .build();
    }

    @Override
    public List<CatalogResponse> toDtos(List<CatalogModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(CatalogRequest request, CatalogModel entity) {}
}
