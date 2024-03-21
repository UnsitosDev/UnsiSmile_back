package edu.mx.unsis.unsiSmile.repository;

import edu.mx.unsis.unsiSmile.model.GenderModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IGenderRepository extends JpaRepository<GenderModel,Long> {
}
