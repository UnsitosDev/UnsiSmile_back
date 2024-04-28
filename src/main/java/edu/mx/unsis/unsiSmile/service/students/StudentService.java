package edu.mx.unsis.unsiSmile.service.students;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.unsis.unsiSmile.dtos.request.students.StudentRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.students.StudentMapper;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.repository.students.IStudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final IStudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Transactional
    public StudentResponse createStudent(StudentRequest request) {
        try {
            StudentModel studentModel = studentMapper.toEntity(request);
            StudentModel savedStudent = studentRepository.save(studentModel);
            return studentMapper.toDto(savedStudent);
        } catch (Exception ex) {
            throw new AppException("Failed to create student", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public StudentResponse getStudentByEnrollment(String enrollment) {
        try {
            StudentModel studentModel = studentRepository.findById(enrollment)
                    .orElseThrow(() -> new AppException("Student not found with enrollment: " + enrollment, HttpStatus.NOT_FOUND));
            return studentMapper.toDto(studentModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch student", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudents() {
        try {
            List<StudentModel> allStudents = studentRepository.findAll();
            return studentMapper.toDtos(allStudents);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch students", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public StudentResponse updateStudent(String enrollment, StudentRequest updatedStudentRequest) {
        try {
            StudentModel studentModel = studentRepository.findById(enrollment)
                    .orElseThrow(() -> new AppException("Student not found with enrollment: " + enrollment, HttpStatus.NOT_FOUND));

            studentMapper.updateEntity(updatedStudentRequest, studentModel);

            StudentModel updatedStudent = studentRepository.save(studentModel);

            return studentMapper.toDto(updatedStudent);
        } catch (Exception ex) {
            throw new AppException("Failed to update student", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteStudentByEnrollment(String enrollment) {
        try {
            if (!studentRepository.existsById(enrollment)) {
                throw new AppException("Student not found with enrollment: " + enrollment, HttpStatus.NOT_FOUND);
            }
            studentRepository.deleteById(enrollment);
        } catch (EmptyResultDataAccessException ex) {
            throw new AppException("Student not found with enrollment: " + enrollment, HttpStatus.NOT_FOUND, ex);
        } catch (Exception ex) {
            throw new AppException("Failed to delete student", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
