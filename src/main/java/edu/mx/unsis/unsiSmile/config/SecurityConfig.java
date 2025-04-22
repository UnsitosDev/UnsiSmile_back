package edu.mx.unsis.unsiSmile.config;

import edu.mx.unsis.unsiSmile.authenticationProviders.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Value("${api.detail.name}")
    private String name;

    @Value("${api.detail.version}")
    private String version;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String basePath = "/" + name + "/api/" + version;

        http
          .csrf(AbstractHttpConfigurer::disable)
          .authorizeHttpRequests(auth -> auth

            // 1) Permitir el handshake de SockJS en /notifications y /review
            .requestMatchers(HttpMethod.GET,  basePath + "/notifications/**").permitAll()
            .requestMatchers(HttpMethod.POST, basePath + "/notifications/**").permitAll()
            
            // 2) Rutas de autenticación REST (login, registro, etc.)
            .requestMatchers(basePath + "/auth/**").permitAll()

            // 3) Swagger / OpenAPI
            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()

            // 4) Cualquier otra petición requiere autenticación
            .anyRequest().authenticated()
          )
          .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authenticationProvider(authProvider)
          .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
