package edu.mx.unsis.unsiSmile.mappers.addresses;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.addresses.HousingRequest;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.HousingResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.HousingModel;

@Component
public class HousingMapper implements BaseMapper<HousingResponse, HousingRequest, HousingModel> {

    @Override
    public HousingModel toEntity(HousingRequest dto) {
        return HousingModel.builder()
                .idHousing(dto.getIdHousing())
                .category(dto.getCategory())
                .build();
    }

    @Override
    public HousingResponse toDto(HousingModel entity) {
        return HousingResponse.builder()
                .idHousing(entity.getIdHousing())
                .category(entity.getCategory())
                .build();
    }

    @Override
    public List<HousingResponse> toDtos(List<HousingModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(HousingRequest request, HousingModel entity) {
        entity.setCategory(request.getCategory());
    }
}