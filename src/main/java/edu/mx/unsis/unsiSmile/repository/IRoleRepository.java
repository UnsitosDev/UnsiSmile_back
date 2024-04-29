package edu.mx.unsis.unsiSmile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.RoleModel;

@Repository
public interface IRoleRepository extends JpaRepository<RoleModel, Long> {
}