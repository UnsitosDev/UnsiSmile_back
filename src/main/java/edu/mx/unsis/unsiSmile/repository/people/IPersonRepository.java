package edu.mx.unsis.unsiSmile.repository.people;

import edu.mx.unsis.unsiSmile.model.people.PersonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IPersonRepository extends JpaRepository<PersonModel, String> {

    Optional<PersonModel> findByEmail(String email);
    
    @Query("SELECT p FROM PersonModel p WHERE p.curp = :curp AND p.statusKey = 'A'")
    Optional<PersonModel> findByCurp(@Param("curp") String curp);

    boolean existsByEmail(String email);
}