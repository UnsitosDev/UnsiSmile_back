package edu.mx.unsis.unsiSmile.service.digitizers;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.digitizers.DigitizerPatientRequest;
import edu.mx.unsis.unsiSmile.dtos.response.digitizers.DigitizerPatientResponse;
import edu.mx.unsis.unsiSmile.dtos.response.people.PersonResponse;
import edu.mx.unsis.unsiSmile.dtos.response.users.UserResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.digitizers.DigitizerPatientMapper;
import edu.mx.unsis.unsiSmile.model.digitizers.DigitizerPatientModel;
import edu.mx.unsis.unsiSmile.model.digitizers.MedicalRecordDigitizerModel;
import edu.mx.unsis.unsiSmile.repository.digitizers.IDigitizerPatientRepository;
import edu.mx.unsis.unsiSmile.repository.digitizers.IMedicalRecordDigitizerRepository;
import edu.mx.unsis.unsiSmile.repository.patients.IPatientRepository;
import edu.mx.unsis.unsiSmile.service.users.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DigitizerPatientService {

    private final IMedicalRecordDigitizerRepository digitizerRepository;
    private final IPatientRepository patientRepository;
    private final IDigitizerPatientRepository digitizerPatientRepository;
    private final DigitizerPatientMapper digitizerPatientMapper;
    private final UserService userService;

    @Transactional
    public void createDigitizerPatient(DigitizerPatientRequest request) {
        try {
            Assert.notNull(request, "DigitizerPatientRequest cannot be null");

            validateActiveDigitizer(request.getMedicalRecordDigitizerId());

            patientRepository.findById(request.getPatientId())
                    .orElseThrow(() -> new AppException(String.format(ResponseMessages.PATIENT_NOT_FOUND), HttpStatus.NOT_FOUND));

            validateNoActiveRelationExists(request.getPatientId(), request.getMedicalRecordDigitizerId());

            DigitizerPatientModel model = digitizerPatientMapper.toEntity(request);
            digitizerPatientRepository.save(model);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_CREATE_DIGITIZER_PATIENT_RELATIONSHIP, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private void validateActiveDigitizer(Long id) {
        MedicalRecordDigitizerModel digitizer = digitizerRepository.findById(id)
                .orElseThrow(() -> new AppException(
                        String.format(ResponseMessages.MEDICAL_RECORD_DIGITIZER_NOT_FOUND, id),
                        HttpStatus.NOT_FOUND));

        if (!Constants.ACTIVE.equals(digitizer.getStatusKey())) {
            throw new AppException(ResponseMessages.DIGITIZER_NOT_ACTIVE, HttpStatus.BAD_REQUEST);
        }
    }

    private void validateNoActiveRelationExists(String patientId, Long digitizerId) {
        boolean exists = digitizerPatientRepository
                .findByPatient_IdPatientAndDigitizer_IdMedicalRecordDigitizerAndStatusKey(
                        patientId, digitizerId, Constants.ACTIVE)
                .isPresent();

        if (exists) {
            throw new AppException(ResponseMessages.DIGITIZER_PATIENT_ALREADY_EXISTS, HttpStatus.CONFLICT);
        }
    }

    @Transactional(readOnly = true)
    public DigitizerPatientResponse getDigitizerPatientById(Long id) {
        try {
            DigitizerPatientModel model = digitizerPatientRepository.findByIdDigitizerPatient(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.DIGITIZER_PATIENT_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));

            DigitizerPatientResponse response = digitizerPatientMapper.toDto(model);

            String username = model.getDigitizer().getUser().getUsername();
            PersonResponse person = userService.getPersonByUsername(username);
            response.setPerson(person);

            return response;
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_DIGITIZER_PATIENT, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public Page<DigitizerPatientResponse> getAllDigitizerPatientsByDigitizer(String username, Pageable pageable) {
        try {
            UserResponse user = userService.getCurrentUser();
            MedicalRecordDigitizerModel digitizer = digitizerRepository
                    .findTopByUser_UsernameOrderByCreatedAtDesc(username)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.DIGITIZER_NOT_FOUND_FOR_USER, username),
                            HttpStatus.NOT_FOUND));

            PersonResponse person = userService.getPersonByUsername(digitizer.getUser().getUsername());

            Page<DigitizerPatientModel> results = ERole.ROLE_ADMIN.equals(user.getRole().getRole())
                    ? digitizerPatientRepository.findByDigitizer_User_Username(username, pageable)
                    : digitizerPatientRepository.findActiveByDigitizer(digitizer, pageable);

            return results.map(model -> {
                DigitizerPatientResponse response = digitizerPatientMapper.toDto(model);
                response.setPerson(person);
                return response;
            });
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_MEDICAL_RECORD_DIGITIZERS, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public Page<MedicalRecordDigitizerModel> getDigitizersByPatient(Pageable pageable, String patientId) {
        try {
            Assert.notNull(patientId, ResponseMessages.PATIENT_ID_CANNOT_BE_NULL);
            if (!patientRepository.existsById(patientId)) {
                throw new AppException(ResponseMessages.PATIENT_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
            Page<DigitizerPatientModel> page = digitizerPatientRepository.findByPatientId(patientId, pageable);
            return page.map(DigitizerPatientModel::getDigitizer);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_MEDICAL_RECORD_DIGITIZERS, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteDigitizerPatient(Long id) {
        try {
            Assert.notNull(id, ResponseMessages.DIGITIZER_PATIENT_ID_CANNOT_BE_NULL);
            DigitizerPatientModel digitizerPatient = digitizerPatientRepository.findByIdDigitizerPatient(id)
                    .orElseThrow(() -> new AppException(String.format(ResponseMessages.DIGITIZER_PATIENT_NOT_FOUND, id), HttpStatus.NOT_FOUND));
            digitizerPatient.setStatusKey(Constants.INACTIVE);
            digitizerPatientRepository.save(digitizerPatient);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_DELETE_DIGITIZER_PATIENT, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteAllRelationsByDigitizerId(@NotNull Long digitizerId) {
        try {
            Assert.notNull(digitizerId, ResponseMessages.DIGITIZER_PATIENT_ID_CANNOT_BE_NULL);
            List<DigitizerPatientModel> relations = digitizerPatientRepository.findByIdDigitizerPatientAndStatusKey(
                    digitizerId, Constants.ACTIVE);

            for (DigitizerPatientModel relation : relations) {
                relation.setStatusKey(Constants.INACTIVE);
            }

            digitizerPatientRepository.saveAll(relations);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_DEACTIVATE_DIGITIZER_PATIENT_RELATIONS,
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}