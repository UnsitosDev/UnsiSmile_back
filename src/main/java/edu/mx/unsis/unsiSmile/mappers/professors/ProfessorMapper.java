package edu.mx.unsis.unsiSmile.mappers.professors;

import edu.mx.unsis.unsiSmile.dtos.request.professors.ProfessorRequest;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ProfessorResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.mappers.people.PersonMapper;
import edu.mx.unsis.unsiSmile.mappers.students.CareerMapper;
import edu.mx.unsis.unsiSmile.mappers.users.UserMapper;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProfessorMapper implements BaseMapper<ProfessorResponse, ProfessorRequest, ProfessorModel> {

    private final UserMapper userMapper;
    private final PersonMapper personMapper;
    private final CareerMapper careerMapper;

    @Override
    public ProfessorModel toEntity(ProfessorRequest dto) {
        return ProfessorModel.builder()
                .idProfessor(dto.getEmployeeNumber())
                .career(careerMapper.toEntity(dto.getCareer()))
                .build();
    }

    @Override
    public ProfessorResponse toDto(ProfessorModel entity) {
        return ProfessorResponse.builder()
                .user(userMapper.toDto(entity.getUser()))
                .person(personMapper.toDto(entity.getPerson()))
                .career(careerMapper.toDto(entity.getCareer()))
                .employeeNumber(entity.getIdProfessor())
                .professorStatus(entity.getStatusKey())
                .build();
    }

    @Override
    public List<ProfessorResponse> toDtos(List<ProfessorModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(ProfessorRequest request, ProfessorModel entity) {
        entity.setPerson(personMapper.toEntity(request.getPerson()));
        entity.setCareer(careerMapper.toEntity(request.getCareer()));
    }
}