package unsis.edu.mx.unsiSmile.authenticationProviders.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unsis.edu.mx.unsiSmile.authenticationProviders.dtos.AuthResponse;
import unsis.edu.mx.unsiSmile.authenticationProviders.dtos.RegisterRequest;
import unsis.edu.mx.unsiSmile.authenticationProviders.service.AuthService;
import unsis.edu.mx.unsiSmile.authenticationProviders.dtos.LoginRequest;
import unsis.edu.mx.unsiSmile.dtos.ApiResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
