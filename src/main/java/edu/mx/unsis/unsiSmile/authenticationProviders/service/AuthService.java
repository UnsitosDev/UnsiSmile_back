package edu.mx.unsis.unsiSmile.authenticationProviders.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.AuthResponse;
import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.LoginRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.RegisterRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.jwt.service.JwtService;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.RoleModel;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.authenticationProviders.repositories.UserRepository;
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

    public ResponseEntity<ApiResponse<Object>> login(LoginRequest request) {

        UserModel user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        log.info("User found - ID: " + user.getId());

        if (!user.isEnabled()) {
            throw new AppException("User is inactive", HttpStatus.UNAUTHORIZED);
        }

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            String token = jwtService.getToken(user);

        AuthResponse authResponse = AuthResponse.builder()
                .token(token)
                .build();

        Map<String,Object> objects = new HashMap<>();
        objects.put("token", authResponse.getToken());

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
}
