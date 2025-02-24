package edu.mx.unsis.unsiSmile.authenticationProviders.service;

import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.*;
import edu.mx.unsis.unsiSmile.authenticationProviders.jwt.service.JwtService;
import edu.mx.unsis.unsiSmile.authenticationProviders.jwt.service.RefreshTokenService;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.RoleModel;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.authenticationProviders.repositories.UserRepository;
import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.response.ApiResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.PersonModel;
import edu.mx.unsis.unsiSmile.repository.administrators.IAdministratorRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorRepository;
import edu.mx.unsis.unsiSmile.repository.students.IStudentRepository;
import edu.mx.unsis.unsiSmile.service.mails.EmailService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final EmailService emailService;
    private final IStudentRepository studentRepository;
    private final IAdministratorRepository administratorRepository;
    private final IProfessorRepository professorRepository;
    private final UserDetailsService userDetailsService;

    public ResponseEntity<ApiResponse<Object>> login(LoginRequest request) {

        UserModel user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        log.info("User found - ID: " + user.getId());

        if (!user.isEnabled()) {
            throw new AppException("User is inactive", HttpStatus.UNAUTHORIZED);
        }

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            String token = jwtService.getToken(user);
            String refreshToken = refreshTokenService.createRefreshToken(user).getToken();

            AuthResponse authResponse = AuthResponse.builder()
                    .token(token)
                    .refreshToken(refreshToken)
                    .build();

            Map<String,Object> objects = new HashMap<>();
            objects.put("token", authResponse.getToken());
            objects.put("refreshToken", authResponse.getRefreshToken());

            return ResponseEntity.ok(ApiResponse.<Object>builder()
                    .response(objects)
                    .build());
        }

        throw new AppException("Bad password", HttpStatus.BAD_REQUEST);

    }

    public AuthResponse register(RegisterRequest request) {

        RoleModel role;
        role = new RoleModel();

        role.setRole(ERole.valueOf(request.getRole()));

        UserModel user = UserModel.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .firstLogin(true)
                .build();

        try {
            userRepository.save(user);

            return AuthResponse.builder()
                    .token(jwtService.getToken(user))
                    .build();
        } catch (Exception e) {
            throw new AppException("User already exists", HttpStatus.CONFLICT, e);
        }
    }

    public void updatePassword(PasswordUpdateRequest request) {
        try {
            UserModel currentUser = getCurrentUser();

            validateOldPassword(currentUser, request.getOldPassword());
            validatePasswordsMatch(request.getNewPassword(), request.getConfirmPassword());
            validateNewPassword(currentUser, request.getNewPassword());

            currentUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
            currentUser.setFirstLogin(false);

            userRepository.save(currentUser);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception e) {
            throw new AppException("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private UserModel getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();

            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Current user not found"));
        } else {
            throw new AppException("No user authenticated", HttpStatus.UNAUTHORIZED);
        }
    }

    private void validateOldPassword(UserModel user, String oldPassword) {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new AppException("Invalid old password", HttpStatus.UNAUTHORIZED);
        }
    }

    private void validatePasswordsMatch(String newPassword, String confirmPassword) {
        if (newPassword == null || !newPassword.equals(confirmPassword)) {
            throw new AppException("New password and confirm password do not match", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateNewPassword(UserModel user, String newPassword) {
        try {
            if (passwordEncoder.matches(newPassword, user.getPassword())) {
                throw new AppException("The new password cannot be the same as the current password", HttpStatus.BAD_REQUEST);
            }

            if (!isPasswordStrong(newPassword)) {
                throw new AppException("Weak password: must include at least 8 characters, one uppercase letter, one lowercase letter, one number, and one special character", HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException e) {
            throw new AppException("Invalid or unrecognized character in password", HttpStatus.BAD_REQUEST, e);
        } catch (Exception e) {
            throw e;
        }
    }

    private boolean isPasswordStrong(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$";
        if (!password.matches(passwordPattern)) {
            throw new IllegalArgumentException("Password contains unrecognized or invalid characters");
        }
        return true;
    }

    public void initiatePasswordRecovery(@NonNull String enrollment) {
        try {
            UserModel user = userRepository.findByUsername(enrollment)
                    .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

            String role = user.getRole().getRole().toString();
            PersonModel person = getPersonByRole(user, role);

            String userName = buildFullName(person);
            String token = jwtService.getTokenEmail(user);

            user.setPasswordToken(token);
            userRepository.save(user);

            emailService.sendPasswordRecoveryEmail(userName, person.getEmail(), token, Constants.CHANGE_PASSWORD);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException("Failed to initiate the password recovery process", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private PersonModel getPersonByRole(UserModel userModel, String role) {
        return switch (role) {
            case "ROLE_STUDENT" -> studentRepository.findByUser(userModel)
                    .orElseThrow(() -> new AppException("Student not found", HttpStatus.NOT_FOUND)).getPerson();
            case "ROLE_ADMIN" -> administratorRepository.findByUser(userModel)
                    .orElseThrow(() -> new AppException("Administrator not found", HttpStatus.NOT_FOUND)).getPerson();
            case "ROLE_PROFESSOR" -> professorRepository.findByUser(userModel)
                    .orElseThrow(() -> new AppException("Professor not found", HttpStatus.NOT_FOUND)).getPerson();
            default -> throw new AppException("Invalid role: " + role, HttpStatus.BAD_REQUEST);
        };
    }

    private String buildFullName(PersonModel person) {
        return person.getFirstName() + " " +
                (person.getSecondName() != null ? person.getSecondName() + " " : "") +
                person.getFirstLastName() + " " +
                (person.getSecondLastName() != null ? person.getSecondLastName() : "");
    }

    public void processPasswordChange(PasswordChangeRequest request) {
        try {
            String username = jwtService.getUsernameFromToken(request.getToken());

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            jwtService.isTokenValid(request.getToken(), userDetails);

            UserModel user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

            if (!request.getToken().equals(user.getPasswordToken())) {
                throw new AppException("Token mismatch", HttpStatus.UNAUTHORIZED);
            }

            validatePasswordsMatch(request.getNewPassword(), request.getConfirmPassword());
            validateNewPassword(user, request.getNewPassword());

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));

            user.setPasswordToken(null);

            userRepository.save(user);
        } catch (ExpiredJwtException e) {
            throw new AppException("Expired token", HttpStatus.UNAUTHORIZED, e);
        } catch (InvalidCsrfTokenException e) {
            throw new AppException("Invalid or expired token", HttpStatus.UNAUTHORIZED, e);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException("Failed to process password change", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}
