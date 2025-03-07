package edu.mx.unsis.unsiSmile.mappers.students;

import edu.mx.unsis.unsiSmile.dtos.request.students.StudentRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.mappers.PersonMapper;
import edu.mx.unsis.unsiSmile.mappers.UserMapper;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class StudentMapper implements BaseMapper<StudentResponse, StudentRequest, StudentModel> {
    private final UserMapper userMapper;
    private final PersonMapper personMapper;

    @Override
    public StudentModel toEntity(StudentRequest dto) {
        return StudentModel.builder()
                .enrollment(dto.getEnrollment())
                .person(personMapper.toEntity(dto.getPerson()))
                .build();
    }

    @Override
    public StudentResponse toDto(StudentModel entity) {
        return StudentResponse.builder()
                .enrollment(entity.getEnrollment())
                .user(userMapper.toDto(entity.getUser()))
                .person(personMapper.toDto(entity.getPerson()))
                .studentStatus(entity.getStatusKey())
                .build();
    }

    @Override
    public List<StudentResponse> toDtos(List<StudentModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(StudentRequest request, StudentModel entity) {
        entity.setEnrollment(request.getEnrollment());
        entity.setPerson(personMapper.toEntity(request.getPerson()));
    }
}
