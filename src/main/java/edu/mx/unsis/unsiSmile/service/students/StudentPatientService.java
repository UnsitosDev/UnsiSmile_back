package edu.mx.unsis.unsiSmile.service.students;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.students.StudentPatientRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentPatientResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.students.StudentPatientMapper;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.model.students.StudentPatientModel;
import edu.mx.unsis.unsiSmile.repository.students.IStudentPatientRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentPatientService {

    private final IStudentPatientRepository studentPatientRepository;
    private final StudentPatientMapper studentPatientMapper;

    @Transactional
    public StudentPatientResponse createStudentPatient(@NonNull StudentPatientRequest studentPatientRequest) {
        try {
            Assert.notNull(studentPatientRequest, "StudentPatientRequest cannot be null");
            System.out.println("student patient " + studentPatientRequest);

            StudentPatientModel studentPatientModel = studentPatientMapper.toEntity(studentPatientRequest);
            StudentPatientModel savedStudentPatient = studentPatientRepository.save(studentPatientModel);

            return studentPatientMapper.toDto(savedStudentPatient);
        } catch (Exception ex) {
            throw new AppException("Failed to create student-patient relationship", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public StudentPatientResponse getStudentPatientById(@NonNull Long idStudentPatient) {
        Optional<StudentPatientModel> studentPatientOptional = studentPatientRepository.findByIdStudentPatient(idStudentPatient);
        StudentPatientModel studentPatientModel = studentPatientOptional.orElseThrow(() -> new AppException("Student-patient relationship not found with ID: " + idStudentPatient, HttpStatus.NOT_FOUND));

        return studentPatientMapper.toDto(studentPatientModel);
    }

    @Transactional(readOnly = true)
    public StudentPatientResponse getStudentPatientByPatient(@NonNull PatientModel patient) {
        Optional<StudentPatientModel> studentPatientOptional = studentPatientRepository.findByPatient(patient);
        StudentPatientModel studentPatientModel = studentPatientOptional.orElseThrow(() -> new AppException("Student-patient relationship not found for patient", HttpStatus.NOT_FOUND));

        return studentPatientMapper.toDto(studentPatientModel);
    }

    @Transactional(readOnly = true)
    public StudentPatientResponse getStudentPatientByStudent(@NonNull StudentModel student) {
        Optional<StudentPatientModel> studentPatientOptional = studentPatientRepository.findByStudent(student);
        StudentPatientModel studentPatientModel = studentPatientOptional.orElseThrow(() -> new AppException("Student-patient relationship not found for student", HttpStatus.NOT_FOUND));

        return studentPatientMapper.toDto(studentPatientModel);
    }

    @Transactional(readOnly = true)
    public List<StudentPatientResponse> getAllStudentPatients() {
        List<StudentPatientModel> allStudentPatients = studentPatientRepository.findAll();
        return allStudentPatients.stream()
                .map(studentPatientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public StudentPatientResponse updateStudentPatient(@NonNull Long idStudentPatient, @NonNull StudentPatientRequest updatedStudentPatientRequest) {
        Assert.notNull(updatedStudentPatientRequest, "Updated StudentPatientRequest cannot be null");

        StudentPatientModel studentPatientModel = studentPatientRepository.findByIdStudentPatient(idStudentPatient)
                .orElseThrow(() -> new AppException("Student-patient relationship not found with ID: " + idStudentPatient, HttpStatus.NOT_FOUND));

        studentPatientMapper.updateEntity(updatedStudentPatientRequest, studentPatientModel);
        StudentPatientModel updatedStudentPatient = studentPatientRepository.save(studentPatientModel);

        return studentPatientMapper.toDto(updatedStudentPatient);
    }

    @Transactional
    public void deleteStudentPatientById(@NonNull Long idStudentPatient) {
        if (!studentPatientRepository.existsById(idStudentPatient)) {
            throw new AppException("Student-patient relationship not found with ID: " + idStudentPatient, HttpStatus.NOT_FOUND);
        }
        studentPatientRepository.deleteById(idStudentPatient);
    }
}
