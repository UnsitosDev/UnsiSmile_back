package edu.mx.unsis.unsiSmile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.mx.unsis.unsiSmile.model.PersonModel;

public interface IPersonRepository extends JpaRepository<PersonModel, String> {

    Optional<PersonModel> findByEmail(String email);
}
