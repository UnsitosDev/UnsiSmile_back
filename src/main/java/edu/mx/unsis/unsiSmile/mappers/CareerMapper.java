package edu.mx.unsis.unsiSmile.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.CareerRequest;
import edu.mx.unsis.unsiSmile.dtos.response.CareerResponse;
import edu.mx.unsis.unsiSmile.model.CareerModel;

@Component
public class CareerMapper implements BaseMapper<CareerResponse, CareerRequest, CareerModel> {

    @Override
    public CareerModel toEntity(CareerRequest dto) {
        return CareerModel.builder()
                .idCareer(dto.getIdCareer())
                .career(dto.getCareer())
                .build();
    }

    @Override
    public CareerResponse toDto(CareerModel entity) {
        return CareerResponse.builder()
                .idCareer(entity.getIdCareer())
                .career(entity.getCareer())
                .build();
    }

    @Override
    public List<CareerResponse> toDtos(List<CareerModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(CareerRequest request, CareerModel entity) {
        
        entity.setCareer(request.getCareer());

    }
}
