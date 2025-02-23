package edu.mx.unsis.unsiSmile.mappers.administrators;

import edu.mx.unsis.unsiSmile.dtos.request.administrators.AdministratorRequest;
import edu.mx.unsis.unsiSmile.dtos.response.administrators.AdministratorResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.mappers.PersonMapper;
import edu.mx.unsis.unsiSmile.mappers.UserMapper;
import edu.mx.unsis.unsiSmile.model.administrators.AdministratorModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AdministratorMapper implements BaseMapper<AdministratorResponse, AdministratorRequest, AdministratorModel> {
    private final UserMapper userMapper;
    private final PersonMapper personMapper;

    @Override
    public AdministratorModel toEntity(AdministratorRequest dto) {
        return AdministratorModel.builder()
                .employeeNumber(dto.getEmployeeNumber())
                .person(personMapper.toEntity(dto.getPerson()))
                .build();
    }

    @Override
    public AdministratorResponse toDto(AdministratorModel entity) {
        return AdministratorResponse.builder()
                .employeeNumber(entity.getEmployeeNumber())
                .person(personMapper.toDto(entity.getPerson()))
                .user(userMapper.toDto(entity.getUser()))
                .administratorStatus(entity.getStatusKey())
                .build();
    }

    @Override
    public List<AdministratorResponse> toDtos(List<AdministratorModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(AdministratorRequest request, AdministratorModel entity) {
        entity.setEmployeeNumber(request.getEmployeeNumber());
        entity.setPerson(personMapper.toEntity(request.getPerson()));
        entity.setUser(userMapper.toEntity(request.getUser()));
    }
}
