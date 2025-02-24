package edu.mx.unsis.unsiSmile.authenticationProviders.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.RefreshTokenModel;

public interface IRefreshTokenRepository extends JpaRepository<RefreshTokenModel, Long> {
    Optional<RefreshTokenModel> findByToken(String token);
    int deleteByUserId(String userId);
}
