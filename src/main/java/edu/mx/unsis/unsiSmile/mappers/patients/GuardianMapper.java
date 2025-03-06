package edu.mx.unsis.unsiSmile.mappers.patients;

import java.util.List;
import java.util.stream.Collectors;

import edu.mx.unsis.unsiSmile.mappers.CatalogOptionMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.patients.GuardianRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.GuardianResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.patients.GuardianModel;

@Component
@AllArgsConstructor
public class GuardianMapper implements BaseMapper<GuardianResponse, GuardianRequest, GuardianModel> {
    private final CatalogOptionMapper catalogOptionMapper;
    @Override
    public GuardianModel toEntity(GuardianRequest dto) {
        return GuardianModel.builder()
                .idGuardian(dto.getIdGuardian())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .parentalStatus(catalogOptionMapper.toEntity(dto.getParentalStatus()))
                .doctorName(dto.getDoctorName())          
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
                .parentalStatus(catalogOptionMapper.toDto(entity.getParentalStatus()))
                .doctorName(entity.getDoctorName())          
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
        entity.setFirstName(request.getFirstName() != null ? request.getFirstName() : entity.getFirstName());
        entity.setLastName(request.getLastName() != null ? request.getLastName() : entity.getLastName());
        entity.setPhone(request.getPhone() != null ? request.getPhone() : entity.getPhone());
        entity.setEmail(request.getEmail() != null ? request.getEmail() : entity.getEmail());
        entity.setParentalStatus(request.getParentalStatus() != null ?
                catalogOptionMapper.toEntity(request.getParentalStatus()) : entity.getParentalStatus());
        entity.setDoctorName(request.getDoctorName() != null ? request.getDoctorName() : entity.getDoctorName());
    }
}