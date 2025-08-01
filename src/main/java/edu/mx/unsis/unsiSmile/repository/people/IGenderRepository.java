package edu.mx.unsis.unsiSmile.repository.people;

import edu.mx.unsis.unsiSmile.model.people.GenderModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IGenderRepository extends JpaRepository<GenderModel, Long> {

    Optional<GenderModel> findByGender(String gender);
}