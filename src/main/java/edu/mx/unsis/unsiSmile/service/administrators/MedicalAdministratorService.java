package edu.mx.unsis.unsiSmile.service.administrators;

import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.RegisterRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.administrators.AdministratorRequest;
import edu.mx.unsis.unsiSmile.dtos.request.administrators.AdministratorUpdateRequest;
import edu.mx.unsis.unsiSmile.dtos.response.administrators.AdministratorResponse;
import edu.mx.unsis.unsiSmile.dtos.response.people.PersonResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.administrators.MedicalAdministratorMapper;
import edu.mx.unsis.unsiSmile.mappers.people.PersonMapper;
import edu.mx.unsis.unsiSmile.model.administrators.MedicalAdministratorModel;
import edu.mx.unsis.unsiSmile.model.people.PersonModel;
import edu.mx.unsis.unsiSmile.repository.administrators.IMedicalAdministratorRepository;
import edu.mx.unsis.unsiSmile.repository.users.IUserRepository;
import edu.mx.unsis.unsiSmile.service.people.PersonService;
import edu.mx.unsis.unsiSmile.service.users.UserService;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalAdministratorService {

    private final IMedicalAdministratorRepository medicalAdministratorRepository;
    private final MedicalAdministratorMapper medicalAdministratorMapper;
    private final PersonService personService;
    private final UserService userService;
    private final IUserRepository userRepository;
    private final PersonMapper personMapper;

    @Transactional
    public AdministratorResponse createMedicalAdministrator(@NonNull AdministratorRequest request) {
        try {
            List<String> invalidCurp = new ArrayList<>();
            PersonModel personModel = personService.createPersonEntity(request.getPerson(), invalidCurp);
            if(!invalidCurp.isEmpty()) {
                throw new AppException(invalidCurp.getFirst(), HttpStatus.BAD_REQUEST);
            }

            UserModel userModel = userService.createUser(setCredentials(request));
            MedicalAdministratorModel medicalAdministratorModel = medicalAdministratorMapper.toEntity(request);

            medicalAdministratorModel.setPerson(personModel);
            medicalAdministratorModel.setUser(userModel);

            MedicalAdministratorModel savedMedicalAdministrator = medicalAdministratorRepository.save(medicalAdministratorModel);

            return medicalAdministratorMapper.toDto(savedMedicalAdministrator);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_CREATE_ADMINISTRATOR, HttpStatus.INTERNAL_SERVER_ERROR, ex);        }
    }

    @Transactional(readOnly = true)
    public AdministratorResponse getMedicalAdministratorByEmployeeNumber(@NonNull String employeeNumber) {
        try {
            MedicalAdministratorModel medicalAdministratorModel = this.findById(employeeNumber);
            return medicalAdministratorMapper.toDto(medicalAdministratorModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_ADMINISTRATOR, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public Page<AdministratorResponse> getAllMedicalAdministrators(Pageable pageable, String keyword) {
        try {
            List<String> validStatuses = List.of(Constants.ACTIVE, Constants.INACTIVE);
            Page<MedicalAdministratorModel> medicalAdministrators;

            if (keyword != null && !keyword.isEmpty()) {
                medicalAdministrators = medicalAdministratorRepository.findByKeyword(keyword, validStatuses, pageable);
            } else {
                medicalAdministrators = medicalAdministratorRepository.findByStatusKeyIn(validStatuses, pageable);
            }

            return medicalAdministrators.map(medicalAdministratorMapper::toDto);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_ADMINISTRATORS, HttpStatus.INTERNAL_SERVER_ERROR,
                    ex);
        }
    }

    @Transactional
    public AdministratorResponse updateMedicalAdministrator(@NotBlank String medicalAdministratorId,
            @NonNull AdministratorUpdateRequest updatedAdministratorRequest) {
        try {
            MedicalAdministratorModel updatedMedicalAdministrator = mapMedicalAdministratorData(medicalAdministratorId, updatedAdministratorRequest);

            medicalAdministratorRepository.save(updatedMedicalAdministrator);

            return medicalAdministratorMapper.toDto(updatedMedicalAdministrator);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.ERROR_UPDATING_ADMINISTRATOR, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private MedicalAdministratorModel mapMedicalAdministratorData(String medicalAdministratorId,
            AdministratorUpdateRequest updatedAdministratorRequest) {

        MedicalAdministratorModel currentMedicalAdministrator = this.findById(medicalAdministratorId);

        if (Constants.INACTIVE.equals(currentMedicalAdministrator.getStatusKey())) {
            throw new AppException(ResponseMessages.ERROR_ADMINISTRATOR_INACTIVE, HttpStatus.BAD_REQUEST);
        }

        PersonResponse personResponse = personService.updatePerson(currentMedicalAdministrator.getPerson().getCurp(),
                updatedAdministratorRequest.getPerson());

        // Preserve existing values if request fields are null
        currentMedicalAdministrator.setPerson(personMapper.toEntity(personResponse));

        currentMedicalAdministrator.setEmployeeNumber(updatedAdministratorRequest.getEmployeeNumber() != null
                ? updatedAdministratorRequest.getEmployeeNumber()
                : currentMedicalAdministrator.getEmployeeNumber());

        return currentMedicalAdministrator;
    }

    @Transactional
    public void deleteMedicalAdministratorByEmployeeNumber(@NonNull String employeeNumber) {
        try {
            MedicalAdministratorModel medicalAdministratorModel = this.findById(employeeNumber);
            medicalAdministratorModel.setStatusKey(Constants.DELETED);

            UserModel userModel = medicalAdministratorModel.getUser();
            userModel.setStatus(!userModel.isStatus());

            userRepository.save(userModel);
            medicalAdministratorRepository.save(medicalAdministratorModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_DELETE_ADMINISTRATOR, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private RegisterRequest setCredentials(AdministratorRequest request) {
        return RegisterRequest.builder()
                .password(request.getPerson().getCurp())
                .username(request.getEmployeeNumber())
                .role(ERole.ROLE_MEDICAL_ADMIN.toString())
                .build();
    }

    @Transactional
    public void updateMedicalAdministratorStatus(@NonNull String employeeNumber) {
        try {
            MedicalAdministratorModel medicalAdministratorModel = this.findById(employeeNumber);

            medicalAdministratorModel.setStatusKey(
                    Constants.ACTIVE.equals(medicalAdministratorModel.getStatusKey()) ? Constants.INACTIVE : Constants.ACTIVE);

            UserModel userModel = medicalAdministratorModel.getUser();
            userModel.setStatus(!userModel.isStatus());

            userRepository.save(userModel);
            medicalAdministratorRepository.save(medicalAdministratorModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_UPDATE_ADMINISTRATOR_STATUS, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private MedicalAdministratorModel findById(String employeeNumber) {
        try {
            MedicalAdministratorModel medicalAdministratorModel = medicalAdministratorRepository.findById(employeeNumber)
                    .orElseThrow(() -> new AppException(String.format(ResponseMessages.ADMINISTRATOR_NOT_FOUND, employeeNumber),
                            HttpStatus.NOT_FOUND));

            if (Constants.DELETED.equals(medicalAdministratorModel.getStatusKey())) {
                throw new AppException(ResponseMessages.ERROR_ADMINISTRATOR_DELETED, HttpStatus.BAD_REQUEST);
            }

            return medicalAdministratorModel;
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_ADMINISTRATOR, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}