package edu.mx.unsis.unsiSmile.authenticationProviders.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.AuthResponse;
import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.LoginRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.PasswordUpdateRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.jwt.service.JwtService;
import edu.mx.unsis.unsiSmile.authenticationProviders.jwt.service.RefreshTokenService;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.authenticationProviders.repositories.UserRepository;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.response.ApiResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    public ResponseEntity<ApiResponse<Object>> login(LoginRequest request) {

        UserModel user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ResponseMessages.LOGIN_ERROR, HttpStatus.NOT_FOUND));

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

            return ResponseEntity.ok(ApiResponse.<Object>builder()
                    .response(objects)
                    .build());
        }

        throw new AppException(ResponseMessages.LOGIN_ERROR, HttpStatus.BAD_REQUEST);

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
}
