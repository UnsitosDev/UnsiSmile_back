package edu.mx.unsis.unsiSmile.mappers.students;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.students.StudentRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.mappers.PersonMapper;
import edu.mx.unsis.unsiSmile.mappers.UserMapper;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StudentMapper implements BaseMapper<StudentResponse, StudentRequest, StudentModel> {
    private final UserMapper userMapper;
    private final PersonMapper personMapper;

    @Override
    public StudentModel toEntity(StudentRequest dto) {
        if (dto == null) {
            return null;
        }
        return StudentModel.builder()
                .enrollment(dto.getEnrollment())
                .person(personMapper.toEntity(dto.getPerson()))
                .build();
    }

    @Override
    public StudentResponse toDto(StudentModel entity) {
        if (entity == null) {
            return null;
        }
        return StudentResponse.builder()
                .enrollment(entity.getEnrollment())
                .user(userMapper.toDto(entity.getUser()))
                .person(personMapper.toDto(entity.getPerson()))
                .build();
    }

    @Override
    public List<StudentResponse> toDtos(List<StudentModel> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(StudentRequest request, StudentModel entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setEnrollment(request.getEnrollment());
        entity.setUser(userMapper.toEntity(request.getUser()));
        entity.setPerson(personMapper.toEntity(request.getPerson()));
    }
}
