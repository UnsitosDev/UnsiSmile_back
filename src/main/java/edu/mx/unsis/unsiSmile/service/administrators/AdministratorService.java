package edu.mx.unsis.unsiSmile.service.administrators;

import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.RegisterRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.dtos.request.UserRequest;
import edu.mx.unsis.unsiSmile.dtos.request.administrators.AdministratorRequest;
import edu.mx.unsis.unsiSmile.dtos.response.administrators.AdministratorResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.UserMapper;
import edu.mx.unsis.unsiSmile.mappers.administrators.AdministratorMapper;
import edu.mx.unsis.unsiSmile.model.administrators.AdministratorsModel;
import edu.mx.unsis.unsiSmile.repository.administrators.IAdministratorRepository;
import edu.mx.unsis.unsiSmile.service.UserService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PersonService;
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

    @Transactional
    public AdministratorResponse createAdministrator(AdministratorRequest request) {
        try {
            // Create person
            personService.createPerson(request.getPerson());

            // Create user with datas of person
            UserModel userModel = userService.createUser(setCredentials(request));
            // Map request to entity
            AdministratorsModel administratorModel = administratorMapper.toEntity(request);
            // set user
            administratorModel.setUser(userModel);
            // Save entity
            AdministratorsModel savedAdministrator = administratorRepository.save(administratorModel);

            // Return response
            return administratorMapper.toDto(savedAdministrator);
        } catch (Exception ex) {
            throw new AppException("Failed to create administrator", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public AdministratorResponse getAdministratorByEmployeeNumber(String employeeNumber) {
        try {
            AdministratorsModel administratorModel = administratorRepository.findById(employeeNumber)
                    .orElseThrow(() -> new AppException(
                            "Administrator not found with employee number: " + employeeNumber, HttpStatus.NOT_FOUND));
            return administratorMapper.toDto(administratorModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch administrator", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public AdministratorResponse getAdministratorByUser(UserRequest userRequest) {
        try {
            UserModel userModel = userMapper.toEntity(userRequest);

            AdministratorsModel administratorModel = administratorRepository.findByUser(userModel)
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
            Page<AdministratorsModel> administrators;

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
    public AdministratorResponse updateAdministrator(String employeeNumber,
            AdministratorRequest updatedAdministratorRequest) {
        try {
            AdministratorsModel administratorModel = administratorRepository.findById(employeeNumber)
                    .orElseThrow(() -> new AppException(
                            "Administrator not found with employee number: " + employeeNumber, HttpStatus.NOT_FOUND));

            administratorMapper.updateEntity(updatedAdministratorRequest, administratorModel);

            AdministratorsModel updatedAdministrator = administratorRepository.save(administratorModel);

            return administratorMapper.toDto(updatedAdministrator);
        } catch (Exception ex) {
            throw new AppException("Failed to update administrator", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteAdministratorByEmployeeNumber(String employeeNumber) {
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
}
