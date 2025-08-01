package edu.mx.unsis.unsiSmile.service;

import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.RegisterRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.RoleModel;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.response.people.PersonResponse;
import edu.mx.unsis.unsiSmile.dtos.response.users.UserResponse;
import edu.mx.unsis.unsiSmile.dtos.response.administrators.AdministratorResponse;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ProfessorResponse;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentResponse;
import edu.mx.unsis.unsiSmile.dtos.response.users.UserBaseResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.users.UserMapper;
import edu.mx.unsis.unsiSmile.mappers.administrators.AdministratorMapper;
import edu.mx.unsis.unsiSmile.mappers.professors.ProfessorMapper;
import edu.mx.unsis.unsiSmile.mappers.students.StudentMapper;
import edu.mx.unsis.unsiSmile.mappers.users.*;
import edu.mx.unsis.unsiSmile.model.files.ProfilePictureModel;
import edu.mx.unsis.unsiSmile.model.administrators.AdministratorModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import edu.mx.unsis.unsiSmile.model.digitizers.MedicalRecordDigitizerModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.repository.users.IRoleRepository;
import edu.mx.unsis.unsiSmile.repository.users.IUserRepository;
import edu.mx.unsis.unsiSmile.repository.administrators.IAdministratorRepository;
import edu.mx.unsis.unsiSmile.repository.files.IProfilePictureRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorRepository;
import edu.mx.unsis.unsiSmile.repository.digitizers.IMedicalRecordDigitizerRepository;
import edu.mx.unsis.unsiSmile.repository.students.IStudentRepository;
import edu.mx.unsis.unsiSmile.service.files.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final IStudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final IAdministratorRepository administratorRepository;
    private final AdministratorMapper administratorMapper;
    private final FileStorageService fileStorageService;
    private final IProfilePictureRepository profilePictureRepository;
    private final IRoleRepository roleRepository;
    private final IProfessorRepository professorRepository;

    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList("image/jpeg", "image/png", "image/jpg");
    private final ProfessorMapper professorMapper;
    private final IMedicalRecordDigitizerRepository medicalRecordDigitizerRepository;

    @Transactional
    public UserModel createUser(RegisterRequest request) {
        try {
            if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                throw new AppException(ResponseMessages.USER_ALREADY_EXISTS + ": " + request.getUsername(),
                        HttpStatus.BAD_REQUEST);
            }
            return userRepository.save(setValuesModel(request));
        } catch (ConstraintViolationException e) {
            throw new AppException(ResponseMessages.BAD_REQUEST + ": " + e.getMessage(), HttpStatus.BAD_REQUEST, e);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.USER_ALREADY_EXISTS, HttpStatus.CONFLICT, ex);
        }
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(String id) {
        try {
            UserModel userModel = userRepository.findById(id)
                    .orElseThrow(() -> new AppException(ResponseMessages.USER_NOT_FOUND + " with ID: " + id,
                            HttpStatus.NOT_FOUND));
            return userMapper.toDto(userModel);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        try {
            List<UserModel> allUsers = userRepository.findAll();
            return userMapper.toDtos(allUsers);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();

            UserModel currentUser = userRepository.findByUsername(username)
                    .orElseThrow(() -> new AppException(ResponseMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND));

            if (currentUser != null) {
                return userMapper.toDto(currentUser);
            } else {
                throw new AppException(ResponseMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
        } else {
            throw new AppException(ResponseMessages.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }
    }

    public UserModel setValuesModel(RegisterRequest request) {

        RoleModel role = roleRepository.findByRole(ERole.valueOf(request.getRole()))
                .orElseGet(() -> {
                    RoleModel newRole = new RoleModel();
                    newRole.setRole(ERole.valueOf(request.getRole()));
                    return roleRepository.save(newRole);
                });

        return UserModel.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
    }

    public ResponseEntity<?> getInformationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();

            return getInformationByUsername(username);
        } else {
            throw new AppException(ResponseMessages.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<?> getInformationUser(String username) {
        try {
            return getInformationByUsername(username);
        } catch (AppException ex) {
            throw ex;
        }
    }

    private ResponseEntity<?> getInformationByUsername(String username) {
        try {
            UserModel user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new AppException(ResponseMessages.USER_NOT_FOUND + " with username: " + username,
                            HttpStatus.NOT_FOUND));

            ERole currentRole = user.getRole().getRole();
            // Verificar y manejar el caso de digitizador
            ERole role = getRoleForSwitch(currentRole, user.getUsername());

            return switch (role) {
                case ERole.ROLE_ADMIN -> ResponseEntity
                        .ok(administratorMapper.toDto(administratorRepository.findById(user.getUsername())
                                .orElseThrow(() -> new AppException(
                                        ResponseMessages.USER_NOT_FOUND + " with enrollment: " + user.getUsername(),
                                        HttpStatus.NOT_FOUND))));
                case ERole.ROLE_STUDENT ->
                    ResponseEntity.ok(studentMapper.toDto(studentRepository.findById(user.getUsername())
                            .orElseThrow(() -> new AppException(
                                    ResponseMessages.USER_NOT_FOUND + " with enrollment: " + user.getUsername(),
                                    HttpStatus.NOT_FOUND))));
                case ERole.ROLE_PROFESSOR, ERole.ROLE_CLINICAL_AREA_SUPERVISOR ->
                    ResponseEntity.ok(professorMapper.toDto(professorRepository.findById(user.getUsername())
                            .orElseThrow(() -> new AppException(
                                    ResponseMessages.USER_NOT_FOUND + " with enrollment: " + user.getUsername(),
                                    HttpStatus.NOT_FOUND))));
                default -> throw new AppException(ResponseMessages.ROLE_NOT_FOUND, HttpStatus.NOT_FOUND);
            };
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteUser(String username) {
        try {
            UserModel userModel = userRepository.findByUsername(username)
                    .orElseThrow(() -> new AppException(ResponseMessages.USER_NOT_FOUND + " with username: " + username,
                            HttpStatus.NOT_FOUND));
            userModel.setStatus(false);
            userRepository.save(userModel);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void createOrUpdateProfilePicture(MultipartFile profilePicture) {
        UserResponse currentUser = getCurrentUser();
        UserModel owner = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new AppException(ResponseMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND));

        // Validar el tipo de archivo
        String contentType = profilePicture.getContentType();
        if (!ALLOWED_FILE_TYPES.contains(contentType)) {
            throw new AppException("Tipo de archivo no permitido. Solo se permiten archivos JPEG, PNG y GIF.",
                    HttpStatus.BAD_REQUEST);
        }

        // Verificar si el usuario ya tiene una foto de perfil
        ProfilePictureModel existingProfilePicture = owner.getProfilePicture();
        String oldPicture = null;
        if (existingProfilePicture != null) {
            // Eliminar el archivo antiguo
            fileStorageService.deleteFile(existingProfilePicture.getUrl());
            oldPicture = existingProfilePicture.getIdProfilePicture();
        }

        // Almacenar el nuevo archivo
        String pictureName = fileStorageService.storeFile(profilePicture);

        // Crear o actualizar el modelo de la foto de perfil
        ProfilePictureModel profilePictureModel = new ProfilePictureModel();
        profilePictureModel.setIdProfilePicture(UUID.randomUUID().toString());
        profilePictureModel.setUrl(pictureName);
        profilePictureModel
                .setExtentionPicture(fileStorageService.getFileExtension(profilePicture.getOriginalFilename()));

        owner.setProfilePicture(profilePictureModel);

        userRepository.save(owner);

        // Eliminar el archivo antiguo de la base de datos
        if (oldPicture != null) {
            profilePictureRepository.deleteById(oldPicture);
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> getProfilePicture() {
        UserResponse currentUser = getCurrentUser();
        String id = currentUser.getId();
        UserModel userModel = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ResponseMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND));

        if (userModel.getProfilePicture() == null) {
            throw new AppException(ResponseMessages.NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        String pictureName = userModel.getProfilePicture().getUrl();
        byte[] picture = fileStorageService.loadFile(pictureName);

        Path filePath = Paths.get(Constants.UPLOAD_PATH).resolve(pictureName);
        String contentType;
        try {
            contentType = Files.probeContentType(filePath);
        } catch (IOException e) {
            throw new AppException(ResponseMessages.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + pictureName + "\"")
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(picture.length))
                .body(picture);
    }

    @Transactional
    public void changeRole(String username, String newRole) {
        try {
            UserModel user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new AppException(ResponseMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND));

            ERole roleEnum = ERole.valueOf(newRole);

            RoleModel role = roleRepository.findByRole(roleEnum)
                    .orElseThrow(() -> new AppException(ResponseMessages.ROLE_NOT_FOUND, HttpStatus.NOT_FOUND));

            user.setRole(role);
            userRepository.save(user);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_CHANGE_ROLE, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public UserModel getUserByUsername(String username) {
        try {
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new AppException(ResponseMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        } catch (AppException e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public PersonResponse getPersonByUsername(String username) {
        try {
            ResponseEntity<?> response = getInformationByUsername(username);
            Object body = response.getBody();

            return switch (body) {
                case StudentResponse student -> student.getPerson();
                case ProfessorResponse professor -> professor.getPerson();
                case AdministratorResponse admin -> admin.getPerson();
                case null, default -> throw new AppException(ResponseMessages.ROLE_NOT_FOUND, HttpStatus.NOT_FOUND);
            };

        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private ERole getRoleForSwitch(ERole currentRole, String username) {
        if (currentRole == ERole.ROLE_MEDICAL_RECORD_DIGITIZER) {
            MedicalRecordDigitizerModel digitizer = medicalRecordDigitizerRepository
                    .findTopByUser_UsernameOrderByCreatedAtDesc(username)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.DIGITIZER_NOT_FOUND_FOR_USER, username),
                            HttpStatus.NOT_FOUND));

            return ERole.valueOf(digitizer.getPreviousRole());
        }
        return currentRole;
    }

    @Transactional(readOnly = true)
    public UserBaseResponse getUserBaseByUsername(String username) {
        try {
            UserModel userModel = userRepository.findByUsername(username)
                    .orElseThrow(() -> new AppException(ResponseMessages.USER_NOT_FOUND + " with username: " + username,
                            HttpStatus.NOT_FOUND));

            switch (userModel.getRole().getRole()) {
                case ROLE_ADMIN:
                    AdministratorModel admin = administratorRepository.findByUser(userModel)
                            .orElseThrow(() -> new AppException(
                                    ResponseMessages.USER_NOT_FOUND + " with username: " + username,
                                    HttpStatus.NOT_FOUND));

                    return new AdministratorResponseBuilder().build(admin);
                case ROLE_PROFESSOR:
                    ProfessorModel professor = professorRepository.findByUser(userModel)
                            .orElseThrow(() -> new AppException(
                                    ResponseMessages.USER_NOT_FOUND + " with username: " + username,
                                    HttpStatus.NOT_FOUND));
                    return new ProfessorResponseBuilder().build(professor);
                case ROLE_STUDENT:
                    StudentModel student = studentRepository.findByUser(userModel)
                            .orElseThrow(() -> new AppException(
                                    ResponseMessages.USER_NOT_FOUND + " with username: " + username,
                                    HttpStatus.NOT_FOUND));

                    return new StudentResponseBuilder().build(student);
                case ROLE_MEDICAL_RECORD_DIGITIZER:
                    MedicalRecordDigitizerModel digitizer = medicalRecordDigitizerRepository
                            .findByUser(userModel)
                            .orElseThrow(() -> new AppException(
                                    ResponseMessages.USER_NOT_FOUND + " with username: " + username,
                                    HttpStatus.NOT_FOUND));
                    return new DigitizerResponseBuilder().build(digitizer);
                default:
                    return new BaseUserResponseBuilder().build(userModel);
            }
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public UserModel getUserModelByUsername(String username) {
        try {
            return userRepository.findByUsername(username)
                    .orElse(null);
        } catch (AppException e) {
            throw e;
        }
    }
}
