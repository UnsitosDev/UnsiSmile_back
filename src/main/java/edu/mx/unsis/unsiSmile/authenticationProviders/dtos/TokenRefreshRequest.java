package edu.mx.unsis.unsiSmile.authenticationProviders.dtos;

import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;
}