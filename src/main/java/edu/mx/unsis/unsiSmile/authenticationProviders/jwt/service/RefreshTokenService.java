package edu.mx.unsis.unsiSmile.authenticationProviders.jwt.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.TokenRefreshResponse;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.RefreshTokenModel;
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

    public RefreshTokenModel createRefreshToken(UserModel user) {
        try {
            // Genera un identificador único para el token (jti)
            String jti = UUID.randomUUID().toString();
            // Emite el refresh token como JWT incluyendo el claim "jti"
            String jwtRefreshToken = jwtService.getRefreshToken(user, jti);
            RefreshTokenModel refreshToken = new RefreshTokenModel();
            refreshToken.setUser(user);
            refreshToken.setToken(jwtRefreshToken);
            refreshToken.setExpiryDate(Instant.now().plusMillis(Long.parseLong(refreshTokenDurationMs.trim())));
            return refreshTokenRepository.save(refreshToken);
        } catch (Exception e) {
            throw new AppException("Error creating refresh token", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public TokenRefreshResponse findByToken(String token) {
        try {
            RefreshTokenModel refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new AppException("Refresh token not found", HttpStatus.NOT_FOUND));
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
            throw new AppException("Error finding refresh token", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public TokenRefreshResponse refreshToken(String token) {
        try {
            RefreshTokenModel refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new AppException("Refresh token not found", HttpStatus.NOT_FOUND));

            // Verificar expiración
            verifyExpiration(refreshToken);
            jwtService.isTokenValid(token, refreshToken.getUser());
            UserModel user = refreshToken.getUser();
            // Elimina el token usado (para evitar reuso)
            refreshTokenRepository.delete(refreshToken);
            // Crea un nuevo refresh token y emite un nuevo access token
            RefreshTokenModel newRefreshToken = createRefreshToken(user);
            String newAccessToken = jwtService.getToken(user);
            return TokenRefreshResponse.builder()
                .token(newAccessToken)
                .refreshToken(newRefreshToken.getToken())
                .build();
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException("Error refreshing token", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public RefreshTokenModel verifyExpiration(RefreshTokenModel token) {
        try {
            if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
                refreshTokenRepository.delete(token);
                throw new AppException("Refresh token expired. Please sign in again.", HttpStatus.UNAUTHORIZED);
            }
            return token;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException("Error verifying refresh token expiration", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public int deleteByUserId(String userId) {
        try {
            return refreshTokenRepository.deleteByUserId(userId);
        } catch (Exception e) {
            throw new AppException("Error deleting refresh token by user ID", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
    
    // Método programado que se ejecuta cada hora para eliminar tokens expirados
    @Scheduled(fixedRate = 3600000)
    public void cleanUpExpiredTokens() {
        Instant now = Instant.now();
        List<RefreshTokenModel> expiredTokens = refreshTokenRepository.findAll().stream()
            .filter(token -> token.getExpiryDate().isBefore(now))
            .collect(Collectors.toList());
        if (!expiredTokens.isEmpty()) {
            refreshTokenRepository.deleteAll(expiredTokens);
        }
    }
}