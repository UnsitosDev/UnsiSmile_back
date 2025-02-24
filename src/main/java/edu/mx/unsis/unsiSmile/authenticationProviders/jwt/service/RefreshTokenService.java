package edu.mx.unsis.unsiSmile.authenticationProviders.jwt.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.TokenRefreshResponse;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.RefreshToken;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.authenticationProviders.repositories.IRefreshTokenRepository;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import jakarta.transaction.Transactional;

@Service
public class RefreshTokenService {

    @Value("${jwt.refresh.token.expiration}")
    private String refreshTokenDurationMs;

    private final IRefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    public RefreshTokenService(IRefreshTokenRepository refreshTokenRepository, JwtService jwtService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
    }

    public RefreshToken createRefreshToken(UserModel user) {
        try {
            RefreshToken refreshToken = new RefreshToken();
            refreshToken.setUser(user);
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(Instant.now().plusMillis(Long.parseLong(refreshTokenDurationMs)));
            return refreshTokenRepository.save(refreshToken);
        } catch (Exception e) {
            throw new RuntimeException("Error creating refresh token", e);
        }
    }

    public TokenRefreshResponse findByToken(String token) {
        try {
            RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
            .orElseThrow( () -> new AppException("Refresh token not found", HttpStatus.NOT_FOUND));

            verifyExpiration(refreshToken);

        UserModel user = refreshToken.getUser();
        String newAccessToken = jwtService.getToken(user);

        return TokenRefreshResponse.builder()
            .token(newAccessToken)
            .refreshToken(token)
            .build();

        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error finding refresh token", e);
        }
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        try {
            if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
                refreshTokenRepository.delete(token);
                throw new RuntimeException("Refresh token expired. Please sign in again.");
            }
            return token;
        } catch (Exception e) {
            throw new RuntimeException("Error verifying refresh token expiration", e);
        }
    }

    @Transactional
    public int deleteByUserId(String userId) {
        try {
            return refreshTokenRepository.deleteByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting refresh token by user ID", e);
        }
    }
}