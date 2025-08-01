package edu.mx.unsis.unsiSmile.repository.users;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.RoleModel;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<RoleModel, Long> {
    Optional<RoleModel> findByRole(ERole role);
}