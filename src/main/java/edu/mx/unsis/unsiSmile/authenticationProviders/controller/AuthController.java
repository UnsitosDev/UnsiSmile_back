package edu.mx.unsis.unsiSmile.authenticationProviders.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.AuthResponse;
import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.LoginRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.PasswordUpdateRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.RegisterRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.TokenRefreshRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.TokenRefreshResponse;
import edu.mx.unsis.unsiSmile.authenticationProviders.jwt.service.RefreshTokenService;
import edu.mx.unsis.unsiSmile.authenticationProviders.service.AuthService;
import edu.mx.unsis.unsiSmile.dtos.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/unsismile/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "AUTHENTICATION")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @Operation(summary = "Inicia sesión en la aplicación.")
    @PostMapping(value = "login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PatchMapping("/updatePassword")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) {
        authService.updatePassword(passwordUpdateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        return ResponseEntity.ok(refreshTokenService.findByToken(requestRefreshToken));
    }
}
