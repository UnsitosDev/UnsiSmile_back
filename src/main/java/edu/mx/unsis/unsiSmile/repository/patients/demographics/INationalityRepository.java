package edu.mx.unsis.unsiSmile.repository.patients.demographics;

import edu.mx.unsis.unsiSmile.model.patients.demographics.NationalityModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface INationalityRepository extends JpaRepository<NationalityModel, Long> {

    Optional<NationalityModel> findByIdNationality(Long idNationality);

    Optional<NationalityModel> findByNationality(String nationality);

    @Query("SELECT n FROM NationalityModel n WHERE n.nationality like :keyword%")
    Page<NationalityModel> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}