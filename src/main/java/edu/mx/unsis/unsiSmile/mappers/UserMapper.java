package edu.mx.unsis.unsiSmile.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.dtos.request.users.UserRequest;
import edu.mx.unsis.unsiSmile.dtos.response.users.UserResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserMapper implements BaseMapper<UserResponse, UserRequest, UserModel> {
    private final RoleMapper roleMapper;

    @Override
    public UserModel toEntity(UserRequest dto) {
        if (dto == null) {
            return null;
        }
        return UserModel.builder()
                .id(dto.getIdUser())
                .username(dto.getUsername())
                .role(roleMapper.toEntity(dto.getRole()))
                .build();
    }

    @Override
    public UserResponse toDto(UserModel entity) {
        if (entity == null) {
            return null;
        }
        return UserResponse.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .role(roleMapper.toDto(entity.getRole()))
                .status(entity.isStatus())
                .profilePictureId(entity.getProfilePicture() != null ? entity.getProfilePicture().getIdProfilePicture() : null)
                .build();
    }

    @Override
    public List<UserResponse> toDtos(List<UserModel> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(UserRequest request, UserModel entity) {
        /*
         * if (request == null || entity == null) {
         * return;
         * }
         * entity.setUsername(request.getUsername());
         */
    }

    
}
