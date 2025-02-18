package edu.mx.unsis.unsiSmile.repository.professors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;

@Repository
public interface IProfessorRepository extends JpaRepository<ProfessorModel, String> {

    @Query("SELECT p FROM ProfessorModel p WHERE p.idProfessor LIKE %:keyword% " +
                "OR p.person.curp LIKE %:keyword% " +
                "OR p.person.firstName LIKE %:keyword% " +
                "OR p.person.secondName LIKE %:keyword% " +
                "OR p.person.firstLastName LIKE %:keyword% " +
                "OR p.person.secondLastName LIKE %:keyword% " +
                "OR p.person.phone LIKE %:keyword% " +
                "OR p.person.email LIKE %:keyword% " +
                "AND p.statusKey='A'")
        Page<ProfessorModel> findAllBySearchInput(
                @Param("keyword") String keyword, Pageable pageable);

}
