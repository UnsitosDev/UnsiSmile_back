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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

            studentGroupRepository.disableLatestStudentGroup(request.getEnrollment());

            StudentGroupModel newStudentGroup = this.toEntity(request);
            studentGroupRepository.save(newStudentGroup);
        } catch (AppException e) {
            throw e;
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

    @Transactional(readOnly = true)
    public Page<StudentGroupModel> getAllStudentsInGroups(Pageable pageable, String keyword) {
        try {
            Page<StudentGroupModel> allStudentGroups;

            if (keyword == null || keyword.isEmpty()) {
                allStudentGroups = studentGroupRepository.findAllActive(pageable);
            } else {
                Integer keywordInt = null;
                if (keyword.matches("\\d+")) {
                    keywordInt = Integer.parseInt(keyword);
                } else if (!keyword.matches("[a-zA-Z]+")) {
                    throw new AppException(ResponseMessages.INVALID_INPUT,
                            HttpStatus.BAD_REQUEST);
                }

                allStudentGroups = studentGroupRepository.findAllBySearchInput(keyword, keywordInt, pageable);
            }

            return allStudentGroups;
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_STUDENTS_IN_GROUPS, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public StudentGroupModel getStudentGroup(String enrollment) {
        return studentGroupRepository.findLatestActiveByStudent(enrollment).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<StudentGroupModel> getAllStudentGroupsByStudent(StudentModel student) {
        return studentGroupRepository.findAllByStudent(student);
    }

    @Transactional(readOnly = true)
    public StudentGroupModel getStudentGroupByStudent(String enrollment) {
        try {
            return studentGroupRepository.findLatestActiveByStudent(enrollment)
                    .orElseThrow(() -> new AppException(ResponseMessages.STUDENT_GROUP_NOT_FOUND, HttpStatus.NOT_FOUND));
        } catch (AppException e)  {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_STUDENT_GROUP, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}