package edu.mx.unsis.unsiSmile.authenticationProviders.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.RefreshToken;

public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    int deleteByUserId(String userId);
}
