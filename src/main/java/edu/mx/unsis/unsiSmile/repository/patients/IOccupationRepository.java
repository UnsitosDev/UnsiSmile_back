package edu.mx.unsis.unsiSmile.repository.patients;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.OccupationModel;

@Repository
public interface IOccupationRepository extends JpaRepository<OccupationModel, Long> {

    Optional<OccupationModel> findByIdOccupation(Long idOccupation);

    Optional<OccupationModel> findByOccupation(String occupation);

}
