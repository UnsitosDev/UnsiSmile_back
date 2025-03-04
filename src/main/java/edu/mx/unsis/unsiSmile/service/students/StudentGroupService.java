package edu.mx.unsis.unsiSmile.service.students;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.students.StudentGroupRequest;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.students.GroupModel;
import edu.mx.unsis.unsiSmile.model.students.StudentGroupModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.repository.students.IGroupRepository;
import edu.mx.unsis.unsiSmile.repository.students.IStudentGroupRepository;
import edu.mx.unsis.unsiSmile.repository.students.IStudentRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StudentGroupService {
    private final IStudentGroupRepository studentGroupRepository;
    private final IStudentRepository studentRepository;
    private final IGroupRepository groupRepository;

    @Transactional
    public void createStudentGroup(@NonNull StudentGroupRequest request) {
        try {
            if (studentGroupRepository.findByStudentAndGroup(request.getEnrollment(), request.getGroupId()).isPresent()) {
                return;
            }

            studentRepository.findById(request.getEnrollment())
                    .orElseThrow(() -> new AppException(ResponseMessages.STUDENT_NOT_FOUND, HttpStatus.NOT_FOUND));

            groupRepository.findById(request.getGroupId())
                    .orElseThrow(() -> new AppException(ResponseMessages.GROUP_NOT_FOUND, HttpStatus.NOT_FOUND));

            studentGroupRepository.deactivateLatestStudentGroup(request.getEnrollment());

            StudentGroupModel newStudentGroup = this.toEntity(request);
            studentGroupRepository.save(newStudentGroup);
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_CREATING_STUDENT_GROUP, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private StudentGroupModel toEntity(StudentGroupRequest request) {
        return StudentGroupModel.builder()
                .student(StudentModel.builder().enrollment(request.getEnrollment()).build())
                .group(GroupModel.builder().idGroup(request.getGroupId()).build())
                .build();
    }
}