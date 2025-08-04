package edu.mx.unsis.unsiSmile.mappers.administrators;

import edu.mx.unsis.unsiSmile.dtos.request.administrators.AdministratorRequest;
import edu.mx.unsis.unsiSmile.dtos.response.administrators.AdministratorResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.mappers.people.PersonMapper;
import edu.mx.unsis.unsiSmile.mappers.users.UserMapper;
import edu.mx.unsis.unsiSmile.model.administrators.MedicalAdministratorModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MedicalAdministratorMapper implements BaseMapper<AdministratorResponse, AdministratorRequest, MedicalAdministratorModel> {
    private final UserMapper userMapper;
    private final PersonMapper personMapper;

    @Override
    public MedicalAdministratorModel toEntity(AdministratorRequest dto) {
        return MedicalAdministratorModel.builder()
                .employeeNumber(dto.getEmployeeNumber())
                .person(personMapper.toEntity(dto.getPerson()))
                .build();
    }

    @Override
    public AdministratorResponse toDto(MedicalAdministratorModel entity) {
        return AdministratorResponse.builder()
                .employeeNumber(entity.getEmployeeNumber())
                .person(personMapper.toDto(entity.getPerson()))
                .user(userMapper.toDto(entity.getUser()))
                .administratorStatus(entity.getStatusKey())
                .build();
    }

    @Override
    public List<AdministratorResponse> toDtos(List<MedicalAdministratorModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(AdministratorRequest request, MedicalAdministratorModel entity) {
        entity.setEmployeeNumber(request.getEmployeeNumber());
        entity.setPerson(personMapper.toEntity(request.getPerson()));
    }
}