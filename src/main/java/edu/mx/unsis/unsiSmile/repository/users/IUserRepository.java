package edu.mx.unsis.unsiSmile.repository.users;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface IUserRepository extends JpaRepository<UserModel, String> {

    Optional<UserModel> findByUsername(String email);

    @Modifying
    @Query("UPDATE UserModel u SET u.status = false WHERE u.role.role = :role")
    void disableAllUsers(@Param("role") ERole role);

    @Modifying
    @Query("UPDATE UserModel u SET u.status = true WHERE u.username = :username AND u.role.role = :role")
    void enableUserByStudentId(@Param("username") String username, @Param("role") ERole role);
}
