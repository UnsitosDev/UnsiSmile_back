package edu.mx.unsis.unsiSmile.repository.administrators;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.model.administrators.AdministratorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAdministratorRepository extends JpaRepository<AdministratorModel, String> {
    Optional<AdministratorModel> findByUser(UserModel user);

    @Query("SELECT a FROM AdministratorModel a WHERE " +
            "(a.employeeNumber LIKE %:keyword% " +
            "OR a.person.curp LIKE %:keyword% " +
            "OR a.person.firstName LIKE %:keyword% " +
            "OR a.person.secondName LIKE %:keyword% " +
            "OR a.person.firstLastName LIKE %:keyword% " +
            "OR a.person.secondLastName LIKE %:keyword% " +
            "OR a.person.phone LIKE %:keyword% " +
            "OR a.person.email LIKE %:keyword% " +
            "OR a.person.gender.gender LIKE %:keyword%) " +
            "AND a.statusKey = 'A'")
    Page<AdministratorModel> findByKeyword(
            @Param("keyword") String keyword, Pageable pageable);
}
