package edu.mx.unsis.unsiSmile.service;

import java.util.List;
import java.util.UUID;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.RegisterRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.RoleModel;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.dtos.response.UserResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.UserMapper;
import edu.mx.unsis.unsiSmile.mappers.students.StudentMapper;
import edu.mx.unsis.unsiSmile.repository.IUserRepository;
import edu.mx.unsis.unsiSmile.repository.students.IStudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final IStudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Transactional
    public UserModel createUser(RegisterRequest request) {
        try {
            if (userRepository.findByUsername(request.getUsername()) != null) {
                throw new AppException("Username already exists: " + request.getUsername(), HttpStatus.BAD_REQUEST);
            }
            // seteamos valores a un tipo request
            UserModel savedUser = userRepository.save(setValuesModel(request));
            return savedUser;
        } catch (ConstraintViolationException e) {
            throw new AppException("User data is invalid: " + e.getMessage(), HttpStatus.BAD_REQUEST, e);
        } catch (Exception ex) {
            throw new AppException("User already exists", HttpStatus.CONFLICT, ex);
        }
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(UUID id) {
        try {
            UserModel userModel = userRepository.findById(id)
                    .orElseThrow(() -> new AppException("User not found with ID: " + id, HttpStatus.NOT_FOUND));
            return userMapper.toDto(userModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch user", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        try {
            List<UserModel> allUsers = userRepository.findAll();
            return userMapper.toDtos(allUsers);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch users", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            UserModel currentUser = userRepository.findByUsername(username);

            if (currentUser != null) {
                return userMapper.toDto(currentUser);
            } else {
                throw new AppException("Current user not found", HttpStatus.NOT_FOUND);
            }
        } else {
            throw new AppException("No user authenticated", HttpStatus.UNAUTHORIZED);
        }
    }

    public UserModel setValuesModel(RegisterRequest request) {

        RoleModel role = new RoleModel();
        role.setRole(ERole.valueOf(request.getRole()));

        return UserModel.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .status(true)
                .build();

    }

    public ResponseEntity<?> getInformationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            UserModel currentUser = userRepository.findByUsername(username);
            ResponseEntity<?> owner = null;
            if (currentUser == null) {
                throw new AppException("Current user not found", HttpStatus.NOT_FOUND);
            }

            switch (currentUser.getRole().getRole()) {
                case ERole.ROLE_ADMIN:
                    break;
                case ERole.ROLE_STUDENT:
                    owner = ResponseEntity.ok(studentMapper.toDto(studentRepository.findById(currentUser.getUsername())
                            .orElseThrow(() -> new AppException(
                                    "Student not found with enrollment: " + currentUser.getUsername(),
                                    HttpStatus.NOT_FOUND))));
                    break;
                default:
                    break;
            }
            return owner;
        } else {
            throw new AppException("No user authenticated", HttpStatus.UNAUTHORIZED);
        }
    }

}
