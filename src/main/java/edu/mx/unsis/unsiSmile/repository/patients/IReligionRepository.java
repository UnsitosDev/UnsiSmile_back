package edu.mx.unsis.unsiSmile.repository.patients;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.ReligionModel;

@Repository
public interface IReligionRepository extends JpaRepository<ReligionModel, Long> {

    Optional<ReligionModel> findByIdReligion(Long idReligion);

    Optional<ReligionModel> findByReligion(String religion);

}