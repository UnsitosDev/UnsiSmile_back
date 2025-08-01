package edu.mx.unsis.unsiSmile.repository.patients.demographics;

import edu.mx.unsis.unsiSmile.model.patients.demographics.MaritalStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMaritalStatusRepository extends JpaRepository<MaritalStatusModel, Long> {

    Optional<MaritalStatusModel> findByIdMaritalStatus(Long idMaritalStatus);

    Optional<MaritalStatusModel> findByMaritalStatus(String maritalStatus);
}