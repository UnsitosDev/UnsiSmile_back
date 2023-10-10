package edu.mx.unsis.unsiSmile.authenticationProviders.controller;

import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.AuthResponse;
import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.LoginRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.mx.unsis.unsiSmile.authenticationProviders.service.AuthService;
import edu.mx.unsis.unsiSmile.dtos.ApiResponse;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping(value = "login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);

        Map<String,Object> objects = new HashMap<>();
        objects.put("token",authResponse.getToken());

        ApiResponse<Object> response = ApiResponse.<Object>builder()
                .response(objects)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

}
