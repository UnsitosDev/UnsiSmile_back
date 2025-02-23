package edu.mx.unsis.unsiSmile.service.administrators;

import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.RegisterRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.UserRequest;
import edu.mx.unsis.unsiSmile.dtos.request.administrators.AdministratorRequest;
import edu.mx.unsis.unsiSmile.dtos.response.administrators.AdministratorResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.UserMapper;
import edu.mx.unsis.unsiSmile.mappers.administrators.AdministratorMapper;
import edu.mx.unsis.unsiSmile.model.PersonModel;
import edu.mx.unsis.unsiSmile.model.administrators.AdministratorModel;
import edu.mx.unsis.unsiSmile.repository.IUserRepository;
import edu.mx.unsis.unsiSmile.repository.administrators.IAdministratorRepository;
import edu.mx.unsis.unsiSmile.service.UserService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PersonService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdministratorService {

    private final IAdministratorRepository administratorRepository;
    private final AdministratorMapper administratorMapper;
    private final PersonService personService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final IUserRepository userRepository;

    @Transactional
    public AdministratorResponse createAdministrator(@NonNull AdministratorRequest request) {
        try {
            PersonModel personModel = personService.createPersonEntity(request.getPerson());

            UserModel userModel = userService.createUser(setCredentials(request));
            AdministratorModel administratorModel = administratorMapper.toEntity(request);

            administratorModel.setPerson(personModel);
            administratorModel.setUser(userModel);

            AdministratorModel savedAdministrator = administratorRepository.save(administratorModel);

            return administratorMapper.toDto(savedAdministrator);
        } catch (Exception ex) {
            throw new AppException("Failed to create administrator", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public AdministratorResponse getAdministratorByEmployeeNumber(@NonNull String employeeNumber) {
        try {
            AdministratorModel administratorModel = administratorRepository.findById(employeeNumber)
                    .orElseThrow(() -> new AppException(
                            "Administrator not found with employee number: " + employeeNumber, HttpStatus.NOT_FOUND));
            return administratorMapper.toDto(administratorModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch administrator", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public AdministratorResponse getAdministratorByUser(@NonNull UserRequest userRequest) {
        try {
            UserModel userModel = userMapper.toEntity(userRequest);

            AdministratorModel administratorModel = administratorRepository.findByUser(userModel)
                    .orElseThrow(() -> new AppException("Administrator not found for user: " + userRequest,
                            HttpStatus.NOT_FOUND));
            return administratorMapper.toDto(administratorModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch administrator", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public Page<AdministratorResponse> getAllAdministrators(Pageable pageable, String keyword) {
        try {
            Page<AdministratorModel> administrators;

            if (keyword != null && !keyword.isEmpty()) {
                administrators = administratorRepository.findByKeyword(keyword, pageable);
            } else {
                administrators = administratorRepository.findAll(pageable);
            }

            return administrators.map(administratorMapper::toDto);
        } catch (Exception ex) {
            throw new AppException("Error al obtener la lista de administradores", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public AdministratorResponse updateAdministrator(@NonNull String employeeNumber,
            @NonNull AdministratorRequest updatedAdministratorRequest) {
        try {
            AdministratorModel administratorModel = administratorRepository.findById(employeeNumber)
                    .orElseThrow(() -> new AppException(
                            "Administrator not found with employee number: " + employeeNumber, HttpStatus.NOT_FOUND));

            administratorMapper.updateEntity(updatedAdministratorRequest, administratorModel);

            AdministratorModel updatedAdministrator = administratorRepository.save(administratorModel);

            return administratorMapper.toDto(updatedAdministrator);
        } catch (Exception ex) {
            throw new AppException("Failed to update administrator", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteAdministratorByEmployeeNumber(@NonNull String employeeNumber) {
        try {
            if (!administratorRepository.existsById(employeeNumber)) {
                throw new AppException("Administrator not found with employee number: " + employeeNumber,
                        HttpStatus.NOT_FOUND);
            }
            administratorRepository.deleteById(employeeNumber);
        } catch (EmptyResultDataAccessException ex) {
            throw new AppException("Administrator not found with employee number: " + employeeNumber,
                    HttpStatus.NOT_FOUND, ex);
        } catch (Exception ex) {
            throw new AppException("Failed to delete administrator", HttpStatus.INTERNAL_SERVER_ERROR, ex);
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
            AdministratorModel administratorModel = administratorRepository.findById(employeeNumber).orElseThrow(()
                    -> new AppException("Administrator not found with employeeNumber: " + employeeNumber, HttpStatus.NOT_FOUND));

            administratorModel.setStatusKey(Constants.ACTIVE.equals(administratorModel.getStatusKey()) ? Constants.INACTIVE : Constants.ACTIVE);

            UserModel userModel = administratorModel.getUser();
            userModel.setStatus(!userModel.isStatus());

            userRepository.save(userModel);
            administratorRepository.save(administratorModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException("Fail to update administrator status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
