package edu.mx.unsis.unsiSmile.repository.patients;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.patients.MaritalStatusModel;

@Repository
public interface IMaritalStatusRepository extends JpaRepository<MaritalStatusModel, Long> {

    Optional<MaritalStatusModel> findByIdMaritalStatus(Long idMaritalStatus);

    Optional<MaritalStatusModel> findByMaritalStatus(String maritalStatus);

}