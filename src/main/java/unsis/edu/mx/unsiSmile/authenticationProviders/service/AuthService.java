package unsis.edu.mx.unsiSmile.authenticationProviders.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import unsis.edu.mx.unsiSmile.authenticationProviders.dtos.AuthResponse;
import unsis.edu.mx.unsiSmile.authenticationProviders.dtos.LoginRequest;
import unsis.edu.mx.unsiSmile.authenticationProviders.dtos.RegisterRequest;
import unsis.edu.mx.unsiSmile.authenticationProviders.model.ERole;
import unsis.edu.mx.unsiSmile.authenticationProviders.model.RoleModel;
import unsis.edu.mx.unsiSmile.authenticationProviders.model.UserModel;
import unsis.edu.mx.unsiSmile.authenticationProviders.repositories.UserRepository;
import unsis.edu.mx.unsiSmile.jwt.service.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("El usuario no encontrado"));

        String token=jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();

    }

    public AuthResponse register(RegisterRequest request) {

        RoleModel role;
        role = new RoleModel();

        role.setRole(ERole.valueOf(request.getRole()));

        UserModel user = UserModel.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode( request.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();

    }
}
