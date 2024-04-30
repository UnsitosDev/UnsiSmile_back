package edu.mx.unsis.unsiSmile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.GenderModel;

public interface IGenderRepository extends JpaRepository<GenderModel,Long> {
}
