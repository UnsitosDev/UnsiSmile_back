package edu.mx.unsis.unsiSmile.authenticationProviders.repositories;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.OtpTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IOtpTokenRepository extends JpaRepository<OtpTokenModel, String> {

    Optional<OtpTokenModel> findByCode(String token);

    Optional<OtpTokenModel> findByEmail(String email);

    Optional<OtpTokenModel> findByEmailAndPurpose(String email, String purpose);
}
