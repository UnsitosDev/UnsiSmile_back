package edu.mx.unsis.unsiSmile.repository.patients;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.addresses.NationalityModel;

@Repository
public interface INationalityRepository extends JpaRepository<NationalityModel, Long> {

    Optional<NationalityModel> findByIdNationality(Long idNationality);

    Optional<NationalityModel> findByNationality(String nationality);

}