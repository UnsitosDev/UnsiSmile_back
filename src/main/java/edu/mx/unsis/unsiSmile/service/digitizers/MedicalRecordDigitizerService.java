package edu.mx.unsis.unsiSmile.service.digitizers;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.digitizers.MedicalRecordDigitizerRequest;
import edu.mx.unsis.unsiSmile.dtos.response.digitizers.MedicalRecordDigitizerResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.digitizers.MedicalRecordDigitizerMapper;
import edu.mx.unsis.unsiSmile.model.people.PersonModel;
import edu.mx.unsis.unsiSmile.model.digitizers.MedicalRecordDigitizerModel;
import edu.mx.unsis.unsiSmile.repository.digitizers.IMedicalRecordDigitizerRepository;
import edu.mx.unsis.unsiSmile.service.users.UserService;
import edu.mx.unsis.unsiSmile.service.people.PersonService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicalRecordDigitizerService {

    private final IMedicalRecordDigitizerRepository medicalRecordDigitizerRepository;
    private final MedicalRecordDigitizerMapper medicalRecordDigitizerMapper;
    private final UserService userService;
    private final DigitizerPatientService digitizerPatientService;
    private final PersonService personService;

    @Transactional
    public void createDigitizer(@NotNull MedicalRecordDigitizerRequest request) {
        try {
            UserModel user = userService.getUserByUsername(request.getUsername());

            if (ERole.ROLE_MEDICAL_RECORD_DIGITIZER.equals(user.getRole().getRole())) {
                throw new AppException(ResponseMessages.USER_ALREADY_IS_DIGITIZER, HttpStatus.BAD_REQUEST);
            }

            validatePreviousDigitizerCanBeCreated(user);

            MedicalRecordDigitizerModel entity = medicalRecordDigitizerMapper.toEntity(request);
            entity.setUser(user);
            entity.setPreviousRole(user.getRole().getRole().name());
            medicalRecordDigitizerRepository.save(entity);

            userService.changeRole(user.getUsername(), ERole.ROLE_MEDICAL_RECORD_DIGITIZER.toString());
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

            MedicalRecordDigitizerResponse response = medicalRecordDigitizerMapper.toDto(model);
            setPersonName(response, model.getUser().getUsername());

            return response;
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_MEDICAL_RECORD_DIGITIZER, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteDigitizer(Long id) {
        try {
            MedicalRecordDigitizerModel model = medicalRecordDigitizerRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.MEDICAL_RECORD_DIGITIZER_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));

            if (Constants.DELETED.equals(model.getStatusKey())) {
                throw new AppException(ResponseMessages.MEDICAL_RECORD_DIGITIZER_ALREADY_DELETED, HttpStatus.BAD_REQUEST);
            }

            model.setStatusKey(Constants.DELETED);
            medicalRecordDigitizerRepository.save(model);

            digitizerPatientService.deleteAllRelationsByDigitizerId(id);

            String previousRole = model.getPreviousRole();
            userService.changeRole(model.getUser().getUsername(), previousRole);
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
                            ? medicalRecordDigitizerRepository.findAllMedicalRecordDigitizer(Constants.DELETED, pageable)
                            : medicalRecordDigitizerRepository.searchByKeyword(keyword, Constants.DELETED, pageable);

            return models.map(model -> {
                MedicalRecordDigitizerResponse response = medicalRecordDigitizerMapper.toDto(model);
                setPersonName(response, model.getUser().getUsername());
                return response;
            });
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

            if (model.getStatusKey().equals(Constants.DELETED)) {
                throw new AppException(ResponseMessages.MEDICAL_RECORD_DIGITIZER_ALREADY_DELETED, HttpStatus.BAD_REQUEST);
            }

            String previousStatus = model.getStatusKey();
            model.setStatusKey(Constants.ACTIVE.equals(previousStatus) ? Constants.INACTIVE : Constants.ACTIVE);
            medicalRecordDigitizerRepository.save(model);

            String role = Constants.ACTIVE.equals(previousStatus)
                    ? model.getPreviousRole()
                    : ERole.ROLE_MEDICAL_RECORD_DIGITIZER.toString();
            userService.changeRole(model.getUser().getUsername(), role);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_CHANGE_MEDICAL_RECORD_DIGITIZER_STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void validatePreviousDigitizerCanBeCreated(UserModel user) {
        Optional<MedicalRecordDigitizerModel> lastDigitizerOpt =
                medicalRecordDigitizerRepository.findTopByUser_UsernameOrderByCreatedAtDesc(user.getUsername());

        if (lastDigitizerOpt.isPresent()) {
            MedicalRecordDigitizerModel lastDigitizer = lastDigitizerOpt.get();

            if (lastDigitizer.getStatusKey().equals(Constants.INACTIVE)) {
                throw new AppException(
                        ResponseMessages.DIGITIZER_ALREADY_EXISTS_NEEDS_REACTIVATION,
                        HttpStatus.BAD_REQUEST
                );
            }
        }
    }

    @Transactional(readOnly = true)
    public MedicalRecordDigitizerModel getMedicalRecordDigitizerModelByUsername(@NotNull String username) {
        try {
            MedicalRecordDigitizerModel model = medicalRecordDigitizerRepository.findTopByUser_UsernameOrderByCreatedAtDesc(username)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.MEDICAL_RECORD_DIGITIZER_NOT_FOUND_FOR_USER, username),
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

    private void setPersonName(MedicalRecordDigitizerResponse response, String username) {
        String curp = userService.getPersonByUsername(username).getCurp();
        PersonModel person = personService.getPersonModelByCurp(curp);
        response.setDigitizerName(person.getFullName());
    }
}