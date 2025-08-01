package edu.mx.unsis.unsiSmile.mappers.students;

import edu.mx.unsis.unsiSmile.dtos.request.students.SemesterRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.SemesterResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.students.SemesterModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SemesterMapper implements BaseMapper<SemesterResponse, SemesterRequest, SemesterModel> {

    private final CycleMapper cycleMapper;

    @Override
    public SemesterModel toEntity(SemesterRequest dto) {
        return SemesterModel.builder()
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .cycle(cycleMapper.toEntity(dto.getCycle()))
                .build();
    }

    @Override
    public SemesterResponse toDto(SemesterModel entity) {
        return SemesterResponse.builder()
                .idSemester(entity.getIdSemester())
                .semesterName(entity.getSemesterName())
                .fechaInicio(entity.getStartDate())
                .fechaFin(entity.getEndDate())
                .cycle(cycleMapper.toDto(entity.getCycle()))
                .build();
    }

    @Override
    public List<SemesterResponse> toDtos(List<SemesterModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(SemesterRequest request, SemesterModel entity) {
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
        entity.setCycle(cycleMapper.toEntity(request.getCycle()));
    }
}