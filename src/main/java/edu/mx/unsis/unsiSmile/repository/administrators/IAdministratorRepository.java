package edu.mx.unsis.unsiSmile.repository.administrators;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.model.administrators.AdministratorsModel;

@Repository
public interface IAdministratorRepository extends JpaRepository<AdministratorsModel, String> {
    Optional<AdministratorsModel> findByUser(UserModel user);
}
