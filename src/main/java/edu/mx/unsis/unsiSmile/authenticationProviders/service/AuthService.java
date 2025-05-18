package edu.mx.unsis.unsiSmile.authenticationProviders.service;

import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.AuthResponse;
import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.LoginRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.PasswordUpdateRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.jwt.service.JwtService;
import edu.mx.unsis.unsiSmile.authenticationProviders.jwt.service.RefreshTokenService;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.authenticationProviders.repositories.UserRepository;
import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.response.ApiResponse;
import edu.mx.unsis.unsiSmile.dtos.response.PersonResponse;
import edu.mx.unsis.unsiSmile.dtos.response.administrators.AdministratorResponse;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ProfessorResponse;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final OtpTokenService otpTokenService;

    public ResponseEntity<ApiResponse<Object>> login(LoginRequest request) {

        log.info("AUDIT: Login attempt for user: {}", request.getUsername());
        try {
            UserModel user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new AppException(ResponseMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND));

            log.info("User found - ID: " + user.getId());

            if (!user.isEnabled()) {
                throw new AppException(ResponseMessages.LOGIN_ERROR, HttpStatus.UNAUTHORIZED);
            }

            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {

                String token = jwtService.getToken(user);
                String refreshToken = refreshTokenService.createRefreshToken(user).getToken();

                AuthResponse authResponse = AuthResponse.builder()
                        .token(token)
                        .refreshToken(refreshToken)
                        .build();

                Map<String, Object> objects = new HashMap<>();
                objects.put("token", authResponse.getToken());
                objects.put("refreshToken", authResponse.getRefreshToken());

                log.info("AUDIT: Login successful for user: {}", request.getUsername());

                return ResponseEntity.ok(ApiResponse.<Object>builder()
                        .response(objects)
                        .build());
            }

            throw new AppException(ResponseMessages.LOGIN_ERROR, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("AUDIT: Login failed for user: {}. Error: {}", request.getUsername(), e.getMessage());
            throw e;
        }
    }

    public void updatePassword(PasswordUpdateRequest request) {
        log.info("AUDIT: Password update attempt: {}");
        try {
            UserModel currentUser = getCurrentUser();

            validateOldPassword(currentUser, request.getOldPassword());
            validatePasswordsMatch(request.getNewPassword(), request.getConfirmPassword());
            validateNewPassword(currentUser, request.getNewPassword());

            currentUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
            currentUser.setFirstLogin(false);

            userRepository.save(currentUser);

            log.info("AUDIT: Password updated successfully for userId: {}", currentUser.getId());
        } catch (AppException ex) {
            throw ex;
        } catch (Exception e) {
            log.error("AUDIT: Password update failed: {}. Error: {}", e.getMessage());
            throw new AppException(ResponseMessages.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private UserModel getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();

            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(ResponseMessages.USER_NOT_FOUND));
        } else {
            throw new AppException(ResponseMessages.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }
    }

    private void validateOldPassword(UserModel user, String oldPassword) {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new AppException(ResponseMessages.BAD_PASSWORD, HttpStatus.UNAUTHORIZED);
        }
    }

    private void validatePasswordsMatch(String newPassword, String confirmPassword) {
        if (newPassword == null || !newPassword.equals(confirmPassword)) {
            throw new AppException(ResponseMessages.PASSWORDS_DO_NOT_MATCH, HttpStatus.BAD_REQUEST);
        }
    }

    private void validateNewPassword(UserModel user, String newPassword) {
        try {
            if (passwordEncoder.matches(newPassword, user.getPassword())) {
                throw new AppException(ResponseMessages.PASSWORD_SAME_AS_OLD, HttpStatus.BAD_REQUEST);
            }

            String passwordError = getPasswordError(newPassword);
            if (passwordError != null) {
                throw new AppException(passwordError, HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException e) {
            throw new AppException(ResponseMessages.PASSWORD_INVALID_CHARACTER, HttpStatus.BAD_REQUEST, e);
        } catch (Exception e) {
            throw e;
        }
    }

    private String getPasswordError(String password) {
        if (password.length() < 8) {
            return ResponseMessages.PASSWORD_WEAK_LENGTH;
        }

        if (!password.matches(".*[a-z].*")) {
            return ResponseMessages.PASSWORD_WEAK_LOWERCASE;
        }

        if (!password.matches(".*[A-Z].*")) {
            return ResponseMessages.PASSWORD_WEAK_UPPERCASE;
        }

        if (!password.matches(".*\\d.*")) {
            return ResponseMessages.PASSWORD_WEAK_NUMBER;
        }

        if (!password.matches(".*[@$!%*?&].*")) {
            return ResponseMessages.PASSWORD_WEAK_SPECIAL_CHAR;
        }

        for (char c : password.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && !"@$!%*?&".contains(String.valueOf(c))) {
                return String.format(ResponseMessages.PASSWORD_INVALID_SPECIFIC_CHAR, c);
            }
        }
        return null;
    }

    public void resetPasswordToDefault(String username) {
        try {
            ResponseEntity<?> userInformation = userService.getInformationUser(username);

            PersonResponse person = getPersonResponse(userInformation);

            UserModel user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException(ResponseMessages.USER_NOT_FOUND));

            String encodedPassword = passwordEncoder.encode(person.getCurp());

            user.setPassword(encodedPassword);
            user.setFirstLogin(true);
            userRepository.save(user);
        } catch (AppException e) {
            throw e;
        }
    }

    private static PersonResponse getPersonResponse(ResponseEntity<?> userInformation) {
        Object associatedPerson = userInformation.getBody();

        if (associatedPerson == null) {
            throw new AppException(ResponseMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        return switch (associatedPerson) {
            case ProfessorResponse professorResponse -> professorResponse.getPerson();

            case StudentResponse studentResponse -> studentResponse.getPerson();

            case AdministratorResponse administratorResponse ->
                    administratorResponse.getPerson();

            default -> throw new AppException(ResponseMessages.ROLE_NOT_FOUND, HttpStatus.NOT_FOUND);
        };
    }

    @Transactional
    public void sendRecoveryCode(String email) {
        try {
            String subject = Constants.RECOVERY_PASSWORD_SUBJECT;
            String textBody = Constants.RECOVERY_PASSWORD_TEXT_BODY;

            otpTokenService.generateAndSendOtp(email, subject, Constants.RECOVERY_PASSWORD, textBody, null);
        } catch (AppException e) {
            throw e;
        }
    }

}
