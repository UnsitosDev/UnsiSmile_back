package edu.mx.unsis.unsiSmile.repository.patients;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.patients.GuardianModel;

@Repository
public interface IGuardianRepository extends JpaRepository<GuardianModel, Long> {

    Optional<GuardianModel> findByIdGuardian(Long idGuardian);

    Optional<GuardianModel> findByPhone(String phone);

    Optional<GuardianModel> findByEmail(String email);

}