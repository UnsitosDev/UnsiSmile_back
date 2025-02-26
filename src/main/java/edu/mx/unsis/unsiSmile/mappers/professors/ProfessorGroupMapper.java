package edu.mx.unsis.unsiSmile.mappers.professors;

import edu.mx.unsis.unsiSmile.dtos.request.professors.ProfessorGroupRequest;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ProfessorGroupResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.mappers.students.GroupMapper;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorGroupModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import edu.mx.unsis.unsiSmile.model.students.GroupModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProfessorGroupMapper implements BaseMapper<ProfessorGroupResponse, ProfessorGroupRequest, ProfessorGroupModel> {
    private final GroupMapper groupMapper;


    @Override
    public ProfessorGroupModel toEntity(ProfessorGroupRequest dto) {
        return ProfessorGroupModel.builder()
                .idProfessorGroup(dto.getIdProfessorGroup())
                .professor(ProfessorModel.builder()
                        .idProfessor(dto.getEmployeNumber())
                        .build())
                .group(GroupModel.builder()
                        .idGroup(dto.getIdGroup())
                        .build())
                .build();
    }

    @Override
    public ProfessorGroupResponse toDto(ProfessorGroupModel entity) {
        return ProfessorGroupResponse.builder()
                .idProfessorGroup(entity.getIdProfessorGroup())
                .professorName(entity.getProfessor().getPerson().getFirstName()
                        + " " + entity.getProfessor().getPerson().getSecondName()
                        + " " + entity.getProfessor().getPerson().getFirstLastName()
                        + " " + entity.getProfessor().getPerson().getSecondLastName())
                .group(groupMapper.toDto(entity.getGroup()))
                .build();
    }

    @Override
    public List<ProfessorGroupResponse> toDtos(List<ProfessorGroupModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(ProfessorGroupRequest request, ProfessorGroupModel entity) {
        entity.setProfessor(ProfessorModel.builder()
                .idProfessor(request.getEmployeNumber())
                .build());
        entity.setGroup(GroupModel.builder()
                .idGroup(request.getIdGroup())
                .build());
    }
}
