package edu.mx.unsis.unsiSmile.service.students;

import edu.mx.unsis.unsiSmile.dtos.request.students.StudentGroupRequest;
import edu.mx.unsis.unsiSmile.model.students.GroupModel;
import edu.mx.unsis.unsiSmile.model.students.StudentGroupModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.repository.students.IStudentGroupRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentGroupService {
    private final IStudentGroupRepository studentGroupRepository;

    @Transactional
    public void createStudentGroup(@NonNull StudentGroupRequest request) {
        Assert.notNull(request, "StudentGroupRequest cannot be null");

        Optional<StudentGroupModel> studentGroup = studentGroupRepository.findByStudentAndGroup(
                request.getEnrollment(), request.getGroupId());

        if (studentGroup.isEmpty()) {
            StudentGroupModel newStudentGroup = this.toEntity(request);
            studentGroupRepository.save(newStudentGroup);
        }
    }

    private StudentGroupModel toEntity(StudentGroupRequest request) {
        return StudentGroupModel.builder()
                .student(StudentModel.builder().enrollment(request.getEnrollment()).build())
                .group(GroupModel.builder().idGroup(request.getGroupId()).build())
                .build();
    }
}