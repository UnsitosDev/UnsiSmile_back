package edu.mx.unsis.unsiSmile.repository.patients.demographics;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.patients.demographics.ReligionModel;

@Repository
public interface IReligionRepository extends JpaRepository<ReligionModel, Long> {

    Optional<ReligionModel> findByIdReligion(Long idReligion);

    Optional<ReligionModel> findByReligion(String religion);

    @Query("SELECT r FROM ReligionModel r WHERE r.religion LIKE :keyword%")
    Page<ReligionModel> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}