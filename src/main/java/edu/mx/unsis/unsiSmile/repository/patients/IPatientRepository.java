package edu.mx.unsis.unsiSmile.repository.patients;

import edu.mx.unsis.unsiSmile.model.PersonModel;
import edu.mx.unsis.unsiSmile.model.addresses.AddressModel;
import edu.mx.unsis.unsiSmile.model.addresses.NationalityModel;
import edu.mx.unsis.unsiSmile.model.patients.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IPatientRepository extends JpaRepository<PatientModel, UUID> {

    Optional<PatientModel> findByIdPatient(UUID idPatient);

    List<PatientModel> findByAdmissionDate(LocalDate admissionDate);

    List<PatientModel> findByIsMinor(Boolean isMinor);

    List<PatientModel> findByHasDisability(Boolean hasDisability);

    List<PatientModel> findByNationality(NationalityModel nationality);

    List<PatientModel> findByPerson(PersonModel person);

    List<PatientModel> findByAddress(AddressModel address);

    List<PatientModel> findByMaritalStatus(MaritalStatusModel maritalStatus);

    List<PatientModel> findByOccupation(OccupationModel occupation);

    List<PatientModel> findByEthnicGroup(EthnicGroupModel ethnicGroup);

    List<PatientModel> findByReligion(ReligionModel religion);

    List<PatientModel> findByGuardian(GuardianModel guardian);

    @Query("SELECT p FROM PatientModel p WHERE p.person.curp LIKE %:keyword% " +
            "OR p.person.firstName LIKE %:keyword% " +
            "OR p.person.secondName LIKE %:keyword% " +
            "OR p.person.firstLastName LIKE %:keyword% " +
            "OR p.person.secondLastName LIKE %:keyword% " +
            "OR p.address.street.name LIKE %:keyword% " +
            "AND p.statusKey = 'A'")
    Page<PatientModel> findAllBySearchInput(String keyword, Pageable pageable);
}
