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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

}
