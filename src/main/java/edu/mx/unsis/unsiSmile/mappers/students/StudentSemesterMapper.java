package edu.mx.unsis.unsiSmile.mappers.students;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.students.StudentSemesterRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentSemesterResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.students.StudentSemesterModel;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StudentSemesterMapper
        implements BaseMapper<StudentSemesterResponse, StudentSemesterRequest, StudentSemesterModel> {

    private final StudentMapper studentMapper;
    private final SemesterMapper semesterMapper;

    public StudentSemesterModel toEntity(StudentSemesterRequest dto) {
        if (dto == null) {
            return null;
        }
        return StudentSemesterModel.builder()
        .idStudentSemester(dto.getIdStudentSemester())
                .student(studentMapper.toEntity(dto.getStudent()))
                .semester(semesterMapper.toEntity(dto.getSemester()))
                .build();
    }

    public StudentSemesterResponse toDto(StudentSemesterModel entity) {
        if (entity == null) {
            return null;
        }
        return StudentSemesterResponse.builder()
        .idStudentSemester(entity.getIdStudentSemester())
                .student(studentMapper.toDto(entity.getStudent()))
                .semester(semesterMapper.toDto(entity.getSemester()))
                .build();
    }

    public List<StudentSemesterResponse> toDtos(List<StudentSemesterModel> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void updateEntity(StudentSemesterRequest request, StudentSemesterModel entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setStudent(studentMapper.toEntity(request.getStudent()));
        entity.setSemester(semesterMapper.toEntity(request.getSemester()));
    }

}
