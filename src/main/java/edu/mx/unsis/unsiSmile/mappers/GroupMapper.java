package edu.mx.unsis.unsiSmile.mappers;

import java.util.List;
import java.util.stream.Collectors;

import edu.mx.unsis.unsiSmile.service.CareerService;
import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.GroupRequest;
import edu.mx.unsis.unsiSmile.dtos.response.GroupResponse;
import edu.mx.unsis.unsiSmile.model.GroupModel;
import edu.mx.unsis.unsiSmile.repository.CareerRepository;

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
        System.out.println("mapper");

        return GroupModel.builder()
                .idGroup(dto.getIdGroup())
                .groupName(dto.getGroupName())
                .career(careerService.getCareerByCareer(String.valueOf(dto.getCareer())))
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
                .career(careerService.getCareerByCareer(String.valueOf(entity.getCareer())))
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
        entity.setGroupName(request.getGroupName());
        entity.setCareer(careerService.getCareerByCareer(String.valueOf(entity.getCareer())));
    }
}
