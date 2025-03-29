package edu.mx.unsis.unsiSmile.mappers.students;

import edu.mx.unsis.unsiSmile.dtos.request.students.GroupRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.GroupResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.students.GroupModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GroupMapper implements BaseMapper<GroupResponse, GroupRequest, GroupModel> {

    private final CareerMapper careerMapper;
    private final SemesterMapper semesterMapper;

    @Override
    public GroupModel toEntity(GroupRequest dto) {
        return GroupModel.builder()
                .idGroup(dto.getId())
                .groupName(dto.getGroupName())
                .semesterNumber(dto.getSemesterNumber())
                .career(careerMapper.toEntity(dto.getCareer()))
                .build();
    }

    @Override
    public GroupResponse toDto(GroupModel entity) {
        return GroupResponse.builder()
                .idGroup(entity.getIdGroup())
                .groupName(formatGroupName(entity))
                .career(careerMapper.toDto(entity.getCareer()))
                .semester(semesterMapper.toDto(entity.getSemester()))
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

    private String formatGroupName(GroupModel entity) {
        String baseName = entity.getSemesterNumber() + entity.getCareer().getIdCareer();
        return (entity.getGroupName() != null && !entity.getGroupName().isEmpty())
                ? baseName + "-" + entity.getGroupName()
                : baseName;
    }
}
