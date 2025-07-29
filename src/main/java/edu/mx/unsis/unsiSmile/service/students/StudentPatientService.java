package edu.mx.unsis.unsiSmile.service.students;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.students.StudentPatientRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.PatientStudentResponse;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentPatientResponse;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.students.StudentMapper;
import edu.mx.unsis.unsiSmile.mappers.students.StudentPatientMapper;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.model.students.StudentPatientModel;
import edu.mx.unsis.unsiSmile.repository.patients.IPatientRepository;
import edu.mx.unsis.unsiSmile.repository.students.IStudentPatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentPatientService {

    private final IPatientRepository patientRepository;
    private final IStudentPatientRepository studentPatientRepository;
    private final StudentPatientMapper studentPatientMapper;
    private final StudentMapper studentMapper;
    private final StudentService studentService;

    @Transactional
    public void createStudentPatient(@NonNull StudentPatientRequest studentPatientRequest) {
        try {
            Assert.notNull(studentPatientRequest, ResponseMessages.REQUEST_CANNOT_BE_NULL);

            studentService.getStudentByEnrollment(studentPatientRequest.getStudentEnrollment());

            patientRepository.findById(studentPatientRequest.getPatientId())
                    .orElseThrow(() -> new AppException(
                            ResponseMessages.PATIENT_NOT_FOUND + " con id: " + studentPatientRequest.getPatientId(),
                            HttpStatus.NOT_FOUND));

            Optional<StudentPatientModel> exists = studentPatientRepository
                    .findByStudent_EnrollmentAndPatient_IdPatientAndStatusKey(
                            studentPatientRequest.getStudentEnrollment(),
                            studentPatientRequest.getPatientId(),
                            Constants.ACTIVE
                    );

            if (exists.isPresent()) {
                throw new AppException(
                        ResponseMessages.STUDENT_PATIENT_RELATIONSHIP_EXISTS,
                        HttpStatus.CONFLICT);
            }

            StudentPatientModel studentPatientModel = studentPatientMapper.toEntity(studentPatientRequest);
            studentPatientRepository.save(studentPatientModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.STUDENT_PATIENT_CREATION_FAILED,
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public StudentPatientResponse getStudentPatientById(@NonNull Long idStudentPatient) {
        Optional<StudentPatientModel> studentPatientOptional = studentPatientRepository
                .findByIdStudentPatient(idStudentPatient);
        StudentPatientModel studentPatientModel = studentPatientOptional.orElseThrow(() -> new AppException(
                "Student-patient relationship not found with ID: " + idStudentPatient, HttpStatus.NOT_FOUND));

        return studentPatientMapper.toDto(studentPatientModel);
    }

    @Transactional(readOnly = true)
    public StudentPatientResponse getStudentPatientByPatient(@NonNull PatientModel patient) {
        Optional<StudentPatientModel> studentPatientOptional = studentPatientRepository.findByPatient(patient);
        StudentPatientModel studentPatientModel = studentPatientOptional.orElseThrow(
                () -> new AppException("Student-patient relationship not found for patient", HttpStatus.NOT_FOUND));

        return studentPatientMapper.toDto(studentPatientModel);
    }

    @Transactional(readOnly = true)
    public StudentPatientResponse getStudentPatientByStudent(@NonNull StudentModel student) {
        Optional<StudentPatientModel> studentPatientOptional = studentPatientRepository.findByStudent(student);
        StudentPatientModel studentPatientModel = studentPatientOptional.orElseThrow(
                () -> new AppException("Student-patient relationship not found for student", HttpStatus.NOT_FOUND));

        return studentPatientMapper.toDto(studentPatientModel);
    }

    @Transactional(readOnly = true)
    public List<StudentPatientResponse> getAllStudentPatients(String enrollment, String keyword, Pageable pageable) {
        if (enrollment != null) {
            List<StudentPatientModel> studentPatients;
            if (keyword != null && !keyword.isEmpty() && pageable != null) {
                studentPatients = studentPatientRepository.findAllBySearchInput(enrollment, keyword, pageable);
            } else {
                studentPatients  = studentPatientRepository.findAllByStudentEnrollmentAndStatusKey(enrollment, Constants.ACTIVE);
            }

            return studentPatients.stream()
                    .map(studentPatientMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            List<StudentPatientModel> allStudentPatients = studentPatientRepository.findAll();
            return allStudentPatients.stream()
                    .map(studentPatientMapper::toDto)
                    .collect(Collectors.toList());
        }
    }

    @Transactional
    public StudentPatientResponse updateStudentPatient(@NonNull Long idStudentPatient,
            @NonNull StudentPatientRequest updatedStudentPatientRequest) {
        Assert.notNull(updatedStudentPatientRequest, "Updated StudentPatientRequest cannot be null");

        StudentPatientModel studentPatientModel = studentPatientRepository.findByIdStudentPatient(idStudentPatient)
                .orElseThrow(() -> new AppException(
                        "Student-patient relationship not found with ID: " + idStudentPatient, HttpStatus.NOT_FOUND));

        studentPatientMapper.updateEntity(updatedStudentPatientRequest, studentPatientModel);
        StudentPatientModel updatedStudentPatient = studentPatientRepository.save(studentPatientModel);

        return studentPatientMapper.toDto(updatedStudentPatient);
    }

    @Transactional
    public void deleteStudentPatientById(@NonNull Long idStudentPatient) {
        try {
            StudentPatientModel studentPatientModel = studentPatientRepository.findByIdStudentPatient(idStudentPatient)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.STUDENT_PATIENT_NOT_FOUND, idStudentPatient),
                            HttpStatus.NOT_FOUND));
            studentPatientModel.setStatusKey(Constants.INACTIVE);
            studentPatientRepository.save(studentPatientModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.STUDENT_PATIENT_DELETION_FAILED,
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<PatientStudentResponse> getByPatients(Set<String> patientsId) {
        if (patientsId.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<StudentPatientModel> studentPatients = studentPatientRepository.findAllByPatientsId(patientsId);
            return studentPatients.stream()
                    .map(studentPatientMapper::toResponse)
                    .collect(Collectors.toList());
        }
    }

    @Transactional(readOnly = true)
    public Page<StudentResponse> getStudentsByPatient(Pageable pageable, String patientId) {
        try {
            Assert.notNull(patientId, "El campo patientId no puede ser null.");
            if (!patientRepository.existsById(patientId)) {
                throw new AppException("Paciente no encontrado con Id: " + patientId, HttpStatus.NOT_FOUND);
            }
            Page<StudentPatientModel> studentPatientPage = studentPatientRepository.findByPatientId(patientId, pageable);

            return studentPatientPage.map(studentPatient -> {
                StudentModel student = studentPatient.getStudent();
                return studentMapper.toDto(student);
            });
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException("Error al obtener los estudiantes del paciente", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
