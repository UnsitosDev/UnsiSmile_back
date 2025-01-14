package edu.mx.unsis.unsiSmile.authenticationProviders.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, String> {
    Optional<UserModel> findByUsername(String username);
}
