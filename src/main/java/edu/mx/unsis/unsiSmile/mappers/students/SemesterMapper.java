package edu.mx.unsis.unsiSmile.mappers.students;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.students.SemesterRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.SemesterResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.students.SemesterModel;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SemesterMapper implements BaseMapper<SemesterResponse, SemesterRequest, SemesterModel> {

    private final GroupMapper groupMapper;
    private final CycleMapper cycleMapper;

    @Override
    public SemesterModel toEntity(SemesterRequest dto) {
        if (dto == null) {
            return null;
        }
        return SemesterModel.builder()
                .idSemester(dto.getIdSemester())
                .group(groupMapper.toEntity(dto.getGroup()))
                .cycle(cycleMapper.toEntity(dto.getCycle()))
                .build();
    }

    @Override
    public SemesterResponse toDto(SemesterModel entity) {
        if (entity == null) {
            return null;
        }
        return SemesterResponse.builder()
                .idSemester(entity.getIdSemester())
                .group(groupMapper.toDto(entity.getGroup()))
                .cycle(cycleMapper.toDto(entity.getCycle()))
                .build();
    }

    @Override
    public List<SemesterResponse> toDtos(List<SemesterModel> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(SemesterRequest request, SemesterModel entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setIdSemester(request.getIdSemester());
        entity.setGroup(groupMapper.toEntity(request.getGroup()));
        entity.setCycle(cycleMapper.toEntity(request.getCycle()));
    }
}
