package edu.mx.unsis.unsiSmile.repository.patients;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.patients.demographics.OccupationModel;

@Repository
public interface IOccupationRepository extends JpaRepository<OccupationModel, Long> {

    Optional<OccupationModel> findByIdOccupation(Long idOccupation);

    Optional<OccupationModel> findByOccupation(String occupation);

    @Query("SELECT o FROM OccupationModel o WHERE o.occupation LIKE %:keyword%")
    Page<OccupationModel> findAllBySearchInput(@Param("keyword") String keyword, Pageable pageable);


}
