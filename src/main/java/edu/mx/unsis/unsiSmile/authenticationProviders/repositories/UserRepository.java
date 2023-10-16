package edu.mx.unsis.unsiSmile.authenticationProviders.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByUsername(String username);
}
