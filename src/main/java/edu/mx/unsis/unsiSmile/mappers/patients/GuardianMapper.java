package edu.mx.unsis.unsiSmile.mappers.patients;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.patients.GuardianRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.GuardianResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.patients.GuardianModel;

@Component
public class GuardianMapper implements BaseMapper<GuardianResponse, GuardianRequest, GuardianModel> {

    @Override
    public GuardianModel toEntity(GuardianRequest dto) {
        return GuardianModel.builder()
                .idGuardian(dto.getIdGuardian())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .build();
    }

    @Override
    public GuardianResponse toDto(GuardianModel entity) {
        return GuardianResponse.builder()
                .idGuardian(entity.getIdGuardian())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .build();
    }

    @Override
    public List<GuardianResponse> toDtos(List<GuardianModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(GuardianRequest request, GuardianModel entity) {
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setPhone(request.getPhone());
        entity.setEmail(request.getEmail());
    }
}