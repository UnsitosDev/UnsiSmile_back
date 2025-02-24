package edu.mx.unsis.unsiSmile.authenticationProviders.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TokenRefreshResponse {
    private String token;
    private String refreshToken;
}