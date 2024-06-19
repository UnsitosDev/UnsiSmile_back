package edu.mx.unsis.unsiSmile.mappers.administrators;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.administrators.AdministratorRequest;
import edu.mx.unsis.unsiSmile.dtos.response.administrators.AdministratorResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.mappers.PersonMapper;
import edu.mx.unsis.unsiSmile.mappers.UserMapper;
import edu.mx.unsis.unsiSmile.model.administrators.AdministratorsModel;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AdministratorMapper implements BaseMapper<AdministratorResponse, AdministratorRequest, AdministratorsModel> {
    private final UserMapper userMapper;
    private final PersonMapper personMapper;

    @Override
    public AdministratorsModel toEntity(AdministratorRequest dto) {
        if (dto == null) {
            return null;
        }
        return AdministratorsModel.builder()
                .employeeNumber(dto.getEmployeeNumber())
                .person(personMapper.toEntity(dto.getPerson()))
                .build();
    }

    @Override
    public AdministratorResponse toDto(AdministratorsModel entity) {
        if (entity == null) {
            return null;
        }
        return AdministratorResponse.builder()
                .employeeNumber(entity.getEmployeeNumber())
                .person(personMapper.toDto(entity.getPerson()))
                .user(userMapper.toDto(entity.getUser()))
                .build();
    }

    @Override
    public List<AdministratorResponse> toDtos(List<AdministratorsModel> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(AdministratorRequest request, AdministratorsModel entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setEmployeeNumber(request.getEmployeeNumber());
        entity.setPerson(personMapper.toEntity(request.getPerson()));
        entity.setUser(userMapper.toEntity(request.getUser()));
    }
}
