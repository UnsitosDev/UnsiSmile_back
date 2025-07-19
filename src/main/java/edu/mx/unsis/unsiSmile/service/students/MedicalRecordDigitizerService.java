package edu.mx.unsis.unsiSmile.service.students;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.students.MedicalRecordDigitizerRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.MedicalRecordDigitizerResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.students.MedicalRecordDigitizerMapper;
import edu.mx.unsis.unsiSmile.model.students.MedicalRecordDigitizerModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.repository.students.IMedicalRecordDigitizerRepository;
import edu.mx.unsis.unsiSmile.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicalRecordDigitizerService {

    private final IMedicalRecordDigitizerRepository medicalRecordDigitizerRepository;
    private final MedicalRecordDigitizerMapper medicalRecordDigitizerMapper;
    private final StudentService studentService;
    private final UserService userService;
    private final DigitizerPatientService digitizerPatientService;

    @Transactional
    public void createDigitizer(@NotNull MedicalRecordDigitizerRequest request) {
        try {
            StudentModel student = studentService.getStudentModel(request.getIdStudent());

            if (ERole.ROLE_MEDICAL_RECORD_DIGITIZER.equals(student.getUser().getRole().getRole())) {
                throw new AppException(ResponseMessages.STUDENT_ALREADY_IS_DIGITIZER, HttpStatus.BAD_REQUEST);
            }

            validatePreviousDigitizerCanBeCreated(student);

            MedicalRecordDigitizerModel entity = medicalRecordDigitizerMapper.toEntity(request);
            medicalRecordDigitizerRepository.save(entity);

            userService.changeRole(student.getEnrollment(), ERole.ROLE_MEDICAL_RECORD_DIGITIZER.toString());
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.ERROR_CREATING_MEDICAL_RECORD_DIGITIZER, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public MedicalRecordDigitizerResponse getDigitizerById(Long id) {
        try {
            MedicalRecordDigitizerModel model = medicalRecordDigitizerRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.MEDICAL_RECORD_DIGITIZER_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));
            return medicalRecordDigitizerMapper.toDto(model);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_MEDICAL_RECORD_DIGITIZER, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void updateDigitizer(MedicalRecordDigitizerRequest request) {
        try {
            MedicalRecordDigitizerModel model = medicalRecordDigitizerRepository.findById(request.getIdMedicalRecordDigitizer())
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.MEDICAL_RECORD_DIGITIZER_NOT_FOUND, request.getIdMedicalRecordDigitizer()),
                            HttpStatus.NOT_FOUND));
            medicalRecordDigitizerMapper.updateEntity(request, model);
            medicalRecordDigitizerRepository.save(model);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_UPDATE_MEDICAL_RECORD_DIGITIZER, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteDigitizer(Long id) {
        try {
            MedicalRecordDigitizerModel model = medicalRecordDigitizerRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.MEDICAL_RECORD_DIGITIZER_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));

            if (Constants.INACTIVE.equals(model.getStatusKey())) {
                throw new AppException(ResponseMessages.MEDICAL_RECORD_DIGITIZER_ALREADY_DELETED, HttpStatus.BAD_REQUEST);
            }

            model.setStatusKey(Constants.INACTIVE);
            model.setEndDate(LocalDate.now());
            medicalRecordDigitizerRepository.save(model);

            digitizerPatientService.deleteAllRelationsByDigitizerId(id);
            userService.changeRole(model.getStudent().getEnrollment(), ERole.ROLE_STUDENT.toString());
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_DELETE_MEDICAL_RECORD_DIGITIZER, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public Page<MedicalRecordDigitizerResponse> getAllDigitizers(Pageable pageable, String keyword) {
        try {
            Page<MedicalRecordDigitizerModel> models =
                    (keyword == null || keyword.isBlank())
                            ? medicalRecordDigitizerRepository.findAllMedicalRecordDigitizer(pageable)
                            : medicalRecordDigitizerRepository.searchByKeyword(keyword, pageable);

            return models.map(medicalRecordDigitizerMapper::toDto);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_MEDICAL_RECORD_DIGITIZERS, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void changeDigitizerStatus(Long id) {
        try {
            MedicalRecordDigitizerModel model = medicalRecordDigitizerRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.MEDICAL_RECORD_DIGITIZER_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));

            if (model.getEndDate() != null) {
                throw new AppException(ResponseMessages.MEDICAL_RECORD_DIGITIZER_ALREADY_DELETED, HttpStatus.BAD_REQUEST);
            }

            model.setStatusKey(
                    Constants.ACTIVE.equals(model.getStatusKey()) ? Constants.INACTIVE : Constants.ACTIVE);
            medicalRecordDigitizerRepository.save(model);

            String role = Constants.ACTIVE.equals(model.getStatusKey())
                    ? ERole.ROLE_MEDICAL_RECORD_DIGITIZER.toString()
                    : ERole.ROLE_STUDENT.toString();
            userService.changeRole(model.getStudent().getEnrollment(), role);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_CHANGE_MEDICAL_RECORD_DIGITIZER_STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void validatePreviousDigitizerCanBeCreated(StudentModel student) {
        Optional<MedicalRecordDigitizerModel> lastDigitizerOpt =
                medicalRecordDigitizerRepository.findTopByStudent_EnrollmentOrderByCreatedAtDesc(student.getEnrollment());

        if (lastDigitizerOpt.isPresent()) {
            MedicalRecordDigitizerModel lastDigitizer = lastDigitizerOpt.get();

            if (lastDigitizer.getEndDate() == null) {
                throw new AppException(
                        ResponseMessages.DIGITIZER_ALREADY_EXISTS_NEEDS_REACTIVATION,
                        HttpStatus.BAD_REQUEST
                );
            }
        }
    }

    @Transactional(readOnly = true)
    public MedicalRecordDigitizerModel getMedicalRecordDigitizerModelByStudent(@NotNull String studentEnrollment) {
        try {
            MedicalRecordDigitizerModel model = medicalRecordDigitizerRepository.findTopByStudent_EnrollmentOrderByCreatedAtDesc(studentEnrollment)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.MEDICAL_RECORD_DIGITIZER_NOT_FOUND_FOR_STUDENT, studentEnrollment),
                            HttpStatus.NOT_FOUND));
            if (Constants.INACTIVE.equals(model.getStatusKey())) {
                throw new AppException(
                        ResponseMessages.DIGITIZER_NOT_ACTIVE,
                        HttpStatus.BAD_REQUEST);
            }
            return model;
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_MEDICAL_RECORD_DIGITIZER, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}