package edu.mx.unsis.unsiSmile.authenticationProviders.controller;

import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.*;
import edu.mx.unsis.unsiSmile.authenticationProviders.jwt.service.RefreshTokenService;
import edu.mx.unsis.unsiSmile.authenticationProviders.service.AuthService;
import edu.mx.unsis.unsiSmile.authenticationProviders.service.OtpTokenService;
import edu.mx.unsis.unsiSmile.dtos.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de autenticación para la aplicación UnsiSmile.
 */
@RestController
@RequestMapping("/unsismile/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "AUTHENTICATION")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final OtpTokenService otpTokenService;

    /**
     * Inicia sesión en la aplicación.
     *
     * @param request Objeto que contiene las credenciales de inicio de sesión.
     * @return ResponseEntity con la respuesta de la API.
     */
    @Operation(summary = "Inicia sesión en la aplicación.")
    @PostMapping(value = "login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }

    /**
     * Actualiza la contraseña del usuario autenticado.
     *
     * @param passwordUpdateRequest Objeto que contiene la nueva contraseña.
     * @return ResponseEntity con el estado de la operación.
     */
    @Operation(summary = "Actualiza la contraseña del usuario desde su perfil.")
    @PatchMapping("/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) {
        authService.updatePassword(passwordUpdateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Refresca el token de autenticación.
     *
     * @param request Objeto que contiene el token de refresco.
     * @return ResponseEntity con la respuesta del nuevo token.
     */
    @PostMapping("/refresh")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
        return ResponseEntity.ok(refreshTokenService.refreshToken(request.getRefreshToken()));
    }

    @Operation(summary = "Restablece la contraseña de un usuario a la predeterminada (solo ADMIN)")
    @PatchMapping("/password/default")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> resetPasswordToDefault(@RequestParam String username) {
        authService.resetPasswordToDefault(username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Solicita un código OTP para recuperación de contraseña")
    @PostMapping("/password/recovery-request")
    public ResponseEntity<Void> requestPasswordRecovery(
            @RequestBody @Valid PasswordRecoveryRequest request) {
        authService.sendRecoveryCode(request.getEmail());
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Valida el código OTP para recuperación de contraseña")
    @PostMapping("/password/validate-otp")
    public ResponseEntity<OtpValidationResponse> validateOtp(
            @RequestBody @Valid OtpValidationRequest request) {
        OtpValidationResponse response = otpTokenService.validateCode(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Establece una nueva contraseña después de validar el código OTP")
    @PatchMapping("/password/recovery")
    public ResponseEntity<Void> setNewPassword(
            @RequestBody @Valid NewPasswordRequest request) {
        authService.updatePasswordWithRecovery(request);
        return ResponseEntity.ok().build();
    }
}