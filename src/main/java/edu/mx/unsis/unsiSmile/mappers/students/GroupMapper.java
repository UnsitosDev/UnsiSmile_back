package edu.mx.unsis.unsiSmile.mappers.students;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.students.GroupRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.GroupResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.students.CareerModel;
import edu.mx.unsis.unsiSmile.model.students.GroupModel;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class GroupMapper implements BaseMapper<GroupResponse, GroupRequest, GroupModel> {

    private final CareerMapper careerMapper;

    @Override
    public GroupModel toEntity(GroupRequest dto) {
        CareerModel careerMaped = careerMapper.toEntity(dto.getCareer());
        return GroupModel.builder()
                .idGroup(dto.getId())
                .groupName(dto.getGroupName())
                .semesterNumber(dto.getSemesterNumber())
                .career(careerMaped)
                .build();
    }

    @Override
    public GroupResponse toDto(GroupModel entity) {
        return GroupResponse.builder()
                .idGroup(entity.getIdGroup())
                .groupName(entity.getSemesterNumber() +
                        entity.getCareer().getIdCareer() +
                        "-" +
                        entity.getGroupName())
                .build();
    }

    @Override
    public List<GroupResponse> toDtos(List<GroupModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(GroupRequest request, GroupModel entity) {
        entity.setGroupName(request.getGroupName());
    }
}
