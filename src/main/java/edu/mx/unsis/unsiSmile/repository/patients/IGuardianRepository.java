package edu.mx.unsis.unsiSmile.repository.patients;

import edu.mx.unsis.unsiSmile.model.patients.GuardianModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IGuardianRepository extends JpaRepository<GuardianModel, Long> {

    Optional<GuardianModel> findByIdGuardian(Long idGuardian);

    Optional<GuardianModel> findByPerson_CurpAndStatusKey(String curp, String statusKey);
}