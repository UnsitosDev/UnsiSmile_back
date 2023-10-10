package edu.mx.unsis.unsiSmile.authenticationProviders.service;

import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.AuthResponse;
import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.LoginRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.RegisterRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.repositories.UserRepository;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.RoleModel;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.jwt.service.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    public AuthResponse login(LoginRequest request) {

        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow(() ->
                new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if(passwordEncoder.matches(request.getPassword(),user.getPassword())){

            String token=jwtService.getToken(user);

            return AuthResponse.builder()
                    .token(token)
                    .build();
        }

        throw new AppException("Bad password", HttpStatus.BAD_REQUEST);


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

        try{
            userRepository.save(user);

            return AuthResponse.builder()
                    .token(jwtService.getToken(user))
                    .build();
        }catch (Exception e){
            throw new AppException("User already exists", HttpStatus.CONFLICT);
        }


    }
}
