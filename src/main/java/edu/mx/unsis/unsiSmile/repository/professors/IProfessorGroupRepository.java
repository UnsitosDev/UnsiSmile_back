package edu.mx.unsis.unsiSmile.repository.professors;

import edu.mx.unsis.unsiSmile.model.professors.ProfessorGroupModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProfessorGroupRepository extends JpaRepository<ProfessorGroupModel, Long> {

    @Query("SELECT p FROM ProfessorGroupModel p " +
            "WHERE (p.professor.person.firstName LIKE %:keyword% " +
            "OR p.professor.person.secondName LIKE %:keyword% " +
            "OR p.professor.person.firstLastName LIKE %:keyword% " +
            "OR p.professor.person.secondLastName LIKE %:keyword%) " +
            "AND p.statusKey = 'A'")
    Page<ProfessorGroupModel> findAllBySearchInput(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM ProfessorGroupModel p WHERE p.professor.idProfessor = :employeeNumber " +
            "AND p.group.statusKey = :status")
    List<ProfessorGroupModel> findByProfessorAndGroupStatus(@Param("employeeNumber") String employeeNumber, @Param("status") String status);

    Page<ProfessorGroupModel> findAllByProfessor_IdProfessor(String id, Pageable pageable);
}