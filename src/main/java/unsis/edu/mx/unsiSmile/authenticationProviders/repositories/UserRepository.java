package unsis.edu.mx.unsiSmile.authenticationProviders.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import unsis.edu.mx.unsiSmile.authenticationProviders.model.UserModel;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel,Long> {
    Optional<UserModel> findByUsername(String username);
}
