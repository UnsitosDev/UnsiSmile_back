package edu.mx.unsis.unsiSmile.mappers.users;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.RoleModel;
import edu.mx.unsis.unsiSmile.dtos.request.users.RoleRequest;
import edu.mx.unsis.unsiSmile.dtos.response.users.RoleResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RoleMapper implements BaseMapper<RoleResponse, RoleRequest, RoleModel> {

    @Override
    public RoleModel toEntity(RoleRequest dto) {
        if (dto == null) {
            return null;
        }
        return RoleModel.builder()
                .id(dto.getIdRole())
                .role(dto.getRole())
                .build();
    }

    @Override
    public RoleResponse toDto(RoleModel entity) {
        if (entity == null) {
            return null;
        }
        return RoleResponse.builder()
                .idRole(entity.getId())
                .role(entity.getRole())
                .build();
    }

    @Override
    public List<RoleResponse> toDtos(List<RoleModel> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(RoleRequest request, RoleModel entity) {
        /*if (request == null || entity == null) {
            return;
        }
        entity.setRole(request.getRole());*/
    }
}