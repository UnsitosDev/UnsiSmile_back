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
import edu.mx.unsis.unsiSmile.mappers.administrators.AdministratorMapper;
import edu.mx.unsis.unsiSmile.mappers.people.PersonMapper;
import edu.mx.unsis.unsiSmile.mappers.users.UserMapper;
import edu.mx.unsis.unsiSmile.model.administrators.AdministratorModel;
import edu.mx.unsis.unsiSmile.model.people.PersonModel;
import edu.mx.unsis.unsiSmile.repository.administrators.IAdministratorRepository;
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
public class AdministratorService {

    private final IAdministratorRepository administratorRepository;
    private final AdministratorMapper administratorMapper;
    private final PersonService personService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final IUserRepository userRepository;
    private final PersonMapper personMapper;

    @Transactional
    public AdministratorResponse createAdministrator(@NonNull AdministratorRequest request) {
        try {
            List<String> invalidCurp = new ArrayList<>();
            PersonModel personModel = personService.createPersonEntity(request.getPerson(), invalidCurp);
            if(!invalidCurp.isEmpty()) {
                throw new AppException(invalidCurp.getFirst(), HttpStatus.BAD_REQUEST);
            }

            UserModel userModel = userService.createUser(setCredentials(request));
            AdministratorModel administratorModel = administratorMapper.toEntity(request);

            administratorModel.setPerson(personModel);
            administratorModel.setUser(userModel);

            AdministratorModel savedAdministrator = administratorRepository.save(administratorModel);

            return administratorMapper.toDto(savedAdministrator);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_CREATE_ADMINISTRATOR, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public AdministratorResponse getAdministratorByEmployeeNumber(@NonNull String employeeNumber) {
        try {
            AdministratorModel administratorModel = administratorRepository.findById(employeeNumber)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.ADMINISTRATOR_NOT_FOUND, employeeNumber),
                            HttpStatus.NOT_FOUND));
            return administratorMapper.toDto(administratorModel);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_ADMINISTRATOR, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public Page<AdministratorResponse> getAllAdministrators(Pageable pageable, String keyword) {
        try {
            List<String> validStatuses = List.of(Constants.ACTIVE, Constants.INACTIVE);
            Page<AdministratorModel> administrators;

            if (keyword != null && !keyword.isEmpty()) {
                administrators = administratorRepository.findByKeyword(keyword, validStatuses, pageable);
            } else {
                administrators = administratorRepository.findByStatusKeyIn(validStatuses, pageable);
            }

            return administrators.map(administratorMapper::toDto);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_ADMINISTRATORS, HttpStatus.INTERNAL_SERVER_ERROR,
                    ex);
        }
    }

    @Transactional
    public AdministratorResponse updateAdministrator(@NotBlank String administradorId,
            @NonNull AdministratorUpdateRequest updatedAdministratorRequest) {
        try {
            AdministratorModel updatedAdministrator;
            // mapear los datos de la solicitud con la entidad actual
            updatedAdministrator = mapAdministratorData(administradorId, updatedAdministratorRequest);

            administratorRepository.save(updatedAdministrator);

            return administratorMapper.toDto(updatedAdministrator);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.ERROR_UPDATING_ADMINISTRATOR, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private AdministratorModel mapAdministratorData(String administradorId,
            AdministratorUpdateRequest updatedAdministratorRequest) {

        AdministratorModel currentAdministrator = administratorRepository.findById(administradorId)
                .orElseThrow(() -> new AppException(
                                String.format(ResponseMessages.ADMINISTRATOR_NOT_FOUND,
                                        updatedAdministratorRequest.getEmployeeNumber()),
                        HttpStatus.NOT_FOUND));

        PersonResponse personResponse = personService.updatePerson(currentAdministrator.getPerson().getCurp(),
                updatedAdministratorRequest.getPerson());

        // Preserve existing values if request fields are null
        currentAdministrator.setPerson(personMapper.toEntity(personResponse));

        currentAdministrator.setEmployeeNumber(updatedAdministratorRequest.getEmployeeNumber() != null
                ? updatedAdministratorRequest.getEmployeeNumber()
                : currentAdministrator.getEmployeeNumber());

        return currentAdministrator;
    }

    @Transactional
    public void deleteAdministratorByEmployeeNumber(@NonNull String employeeNumber) {
        try {
            AdministratorModel administratorModel = administratorRepository.findById(employeeNumber)
                    .orElseThrow(() -> new AppException(String.format(ResponseMessages.ADMINISTRATOR_NOT_FOUND, employeeNumber),
                            HttpStatus.NOT_FOUND));
            administratorModel.setStatusKey(Constants.DELETED);

            UserModel userModel = administratorModel.getUser();
            userModel.setStatus(!userModel.isStatus());

            userRepository.save(userModel);
            administratorRepository.save(administratorModel);
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
                .role(ERole.ROLE_ADMIN.toString())
                .build();
    }

    @Transactional
    public void updateAdministratorStatus(@NonNull String employeeNumber) {
        try {
            AdministratorModel administratorModel = administratorRepository.findById(employeeNumber).orElseThrow(
                    () -> new AppException(String.format(ResponseMessages.ADMINISTRATOR_NOT_FOUND, employeeNumber),
                            HttpStatus.NOT_FOUND));

            administratorModel.setStatusKey(
                    Constants.ACTIVE.equals(administratorModel.getStatusKey()) ? Constants.INACTIVE : Constants.ACTIVE);

            UserModel userModel = administratorModel.getUser();
            userModel.setStatus(!userModel.isStatus());

            userRepository.save(userModel);
            administratorRepository.save(administratorModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_UPDATE_ADMINISTRATOR_STATUS, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}