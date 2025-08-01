package edu.mx.unsis.unsiSmile.mappers.students;

import edu.mx.unsis.unsiSmile.dtos.request.students.CareerRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.CareerResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.students.CareerModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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