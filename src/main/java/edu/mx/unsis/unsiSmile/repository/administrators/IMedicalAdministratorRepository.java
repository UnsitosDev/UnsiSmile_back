package edu.mx.unsis.unsiSmile.repository.administrators;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.model.administrators.MedicalAdministratorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMedicalAdministratorRepository extends JpaRepository<MedicalAdministratorModel, String> {

    Optional<MedicalAdministratorModel> findByUser(UserModel user);

    @Query("SELECT ma FROM MedicalAdministratorModel ma WHERE " +
            "(ma.employeeNumber LIKE %:keyword% " +
            "OR ma.person.curp LIKE %:keyword% " +
            "OR ma.person.firstName LIKE %:keyword% " +
            "OR ma.person.secondName LIKE %:keyword% " +
            "OR ma.person.firstLastName LIKE %:keyword% " +
            "OR ma.person.secondLastName LIKE %:keyword% " +
            "OR ma.person.phone LIKE %:keyword% " +
            "OR ma.person.email LIKE %:keyword% " +
            "OR ma.person.gender.gender LIKE %:keyword%) " +
            "AND ma.statusKey IN :statusKeys")
    Page<MedicalAdministratorModel> findByKeyword(
            @Param("keyword") String keyword,
            @Param("statusKeys") List<String> statusKeys,
            Pageable pageable);

    Page<MedicalAdministratorModel> findByStatusKeyIn(List<String> statusKeys, Pageable pageable);
}