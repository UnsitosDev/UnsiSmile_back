package edu.mx.unsis.unsiSmile.service.students;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.unsis.unsiSmile.dtos.request.students.StudentSemesterRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentSemesterResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.students.StudentSemesterMapper;
import edu.mx.unsis.unsiSmile.model.students.StudentSemesterModel;
import edu.mx.unsis.unsiSmile.repository.students.IStudentSemesterRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentSemesterService {

    private final IStudentSemesterRepository studentSemesterRepository;
    private final StudentSemesterMapper studentSemesterMapper;

    @Transactional
    public StudentSemesterResponse createStudentSemester(StudentSemesterRequest request) {
        try {
            StudentSemesterModel studentSemesterModel = studentSemesterMapper.toEntity(request);
            StudentSemesterModel savedStudentSemester = studentSemesterRepository.save(studentSemesterModel);
            return studentSemesterMapper.toDto(savedStudentSemester);
        } catch (Exception ex) {
            throw new AppException("Failed to create student semester", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public StudentSemesterResponse getStudentSemesterById(Long id) {
        try {
            StudentSemesterModel studentSemesterModel = studentSemesterRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            "Student semester not found with IDs: " + id ,
                            HttpStatus.NOT_FOUND));
            return studentSemesterMapper.toDto(studentSemesterModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch student semester", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<StudentSemesterResponse> getAllStudentSemesters() {
        try {
            List<StudentSemesterModel> studentSemesters = studentSemesterRepository.findAll();
            return studentSemesterMapper.toDtos(studentSemesters);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch student semesters", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public StudentSemesterResponse updateStudentSemester(Long id, StudentSemesterRequest updatedRequest) {
        try {
            StudentSemesterModel studentSemesterModel = studentSemesterRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            "Student semester not found with IDs: " + id,
                            HttpStatus.NOT_FOUND));

            studentSemesterMapper.updateEntity(updatedRequest, studentSemesterModel);
            studentSemesterRepository.save(studentSemesterModel);
            return studentSemesterMapper.toDto(studentSemesterModel);
        } catch (Exception ex) {
            throw new AppException("Failed to update student semester", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteStudentSemester(Long id) {
        try {
            studentSemesterRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete student semester", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
