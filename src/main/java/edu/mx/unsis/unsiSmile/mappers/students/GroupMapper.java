package edu.mx.unsis.unsiSmile.mappers.students;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.students.GroupRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.GroupResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.students.CareerModel;
import edu.mx.unsis.unsiSmile.model.students.GroupModel;
import edu.mx.unsis.unsiSmile.service.students.CareerService;


@Component
public class GroupMapper implements BaseMapper<GroupResponse, GroupRequest, GroupModel> {

    private final CareerService careerService;

    public GroupMapper(CareerService careerService) {
        this.careerService = careerService;
    }

    @Override
    public GroupModel toEntity(GroupRequest dto) {
        if (dto == null) {
            return null;
        }
        CareerModel career = careerService.getCareerByCareer(dto.getCareer().getCareer());

        return GroupModel.builder()
                .groupName(dto.getGroupName())
                .career(career)
                .build();
    }

    @Override
    public GroupResponse toDto(GroupModel entity) {
        if (entity == null) {
            return null;
        }
        return GroupResponse.builder()
                .idGroup(entity.getIdGroup())
                .groupName(entity.getGroupName())
                .career(entity.getCareer())
                .build();
    }

    @Override
    public List<GroupResponse> toDtos(List<GroupModel> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(GroupRequest request, GroupModel entity) {
        if (request == null || entity == null) {
            return;
        }
        CareerModel career = careerService.getCareerByCareer(request.getCareer().getCareer());
        entity.setGroupName(request.getGroupName());
        entity.setCareer(career);
    }
}
