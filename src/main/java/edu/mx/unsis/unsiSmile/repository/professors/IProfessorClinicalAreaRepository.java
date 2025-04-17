package edu.mx.unsis.unsiSmile.repository.professors;

import edu.mx.unsis.unsiSmile.model.professors.ClinicalAreaModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorClinicalAreaModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProfessorClinicalAreaRepository extends JpaRepository<ProfessorClinicalAreaModel, Long> {
    @Query("SELECT p FROM ProfessorClinicalAreaModel p " +
       "WHERE (p.professor.person.firstName LIKE %:keyword% " +
       "OR p.professor.person.secondName LIKE %:keyword% " +
       "OR p.professor.person.firstLastName LIKE %:keyword% " +
       "OR p.professor.person.secondLastName LIKE %:keyword% " +
       "OR p.clinicalArea.clinicalArea LIKE %:keyword%) AND p.statusKey = 'A'")
    Page<ProfessorClinicalAreaModel> findAllBySearchInput(@Param("keyword") String keyword, Pageable pageable);

    Optional<ProfessorClinicalAreaModel> findByProfessorAndStatusKey(@Param("professor") ProfessorModel professor, @Param("statusKey") String statusKey);

    @Query("SELECT p FROM ProfessorClinicalAreaModel p WHERE p.professor.idProfessor = :professorId AND p.statusKey = 'A'")
    Optional<ProfessorClinicalAreaModel> findByProfessorId(@Param("professorId") String professorId);

    @Query("SELECT p FROM ProfessorClinicalAreaModel p " +
            "WHERE p.clinicalArea = :clinicalArea AND p.statusKey = :statusKey")
    List<ProfessorClinicalAreaModel> findAllByClinicalAreaAndStatusKey(
            @Param("clinicalArea") ClinicalAreaModel clinicalArea,
            @Param("statusKey") String statusKey);

    boolean existsByProfessorAndStatusKey(ProfessorModel professor, String statusKey);
}
