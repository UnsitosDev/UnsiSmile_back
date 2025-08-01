package edu.mx.unsis.unsiSmile.mappers.patients;

import java.util.List;
import java.util.stream.Collectors;

import edu.mx.unsis.unsiSmile.mappers.forms.catalogs.CatalogOptionMapper;
import edu.mx.unsis.unsiSmile.mappers.people.PersonMapper;
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
    private final PersonMapper personMapper;

    @Override
    public GuardianModel toEntity(GuardianRequest dto) {
        return GuardianModel.builder()
                .idGuardian(dto.getIdGuardian())
                .parentalStatus(catalogOptionMapper.toEntity(dto.getParentalStatus()))
                .doctorName(dto.getDoctorName())
                .person(personMapper.toEntity(dto.getPerson()))          
                .build();
    }

    @Override
    public GuardianResponse toDto(GuardianModel entity) {
        return GuardianResponse.builder()
                .idGuardian(entity.getIdGuardian())
                .parentalStatus(catalogOptionMapper.toDto(entity.getParentalStatus()))
                .doctorName(entity.getDoctorName())      
                .person(personMapper.toDto(entity.getPerson()))    
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
        entity.setParentalStatus(request.getParentalStatus() != null ?
                catalogOptionMapper.toEntity(request.getParentalStatus()) : entity.getParentalStatus());
        entity.setDoctorName(request.getDoctorName() != null ? request.getDoctorName() : entity.getDoctorName());
    }

}