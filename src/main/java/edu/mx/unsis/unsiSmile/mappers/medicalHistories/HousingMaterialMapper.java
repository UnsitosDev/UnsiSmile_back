package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.HousingMaterialRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.HousingMaterialResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.HousingMaterialModel;

@Component
public class HousingMaterialMapper implements BaseMapper<HousingMaterialResponse, HousingMaterialRequest, HousingMaterialModel> {

    @Override
    public HousingMaterialModel toEntity(HousingMaterialRequest dto) {
        return HousingMaterialModel.builder()
                .housingMaterialId(dto.getHousingMaterialId())
                .material(dto.getMaterial())
                .build();
    }

    @Override
    public HousingMaterialResponse toDto(HousingMaterialModel entity) {
        return HousingMaterialResponse.builder()
                .housingMaterialId(entity.getHousingMaterialId())
                .material(entity.getMaterial())
                .build();
    }

    @Override
    public List<HousingMaterialResponse> toDtos(List<HousingMaterialModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(HousingMaterialRequest request, HousingMaterialModel entity) {
        entity.setMaterial(request.getMaterial());
    }
}
