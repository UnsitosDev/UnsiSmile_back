package edu.mx.unsis.unsiSmile.repository.patients;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.mx.unsis.unsiSmile.model.PersonModel;
import edu.mx.unsis.unsiSmile.model.addresses.AddressModel;
import edu.mx.unsis.unsiSmile.model.addresses.NationalityModel;
import edu.mx.unsis.unsiSmile.model.patients.EthnicGroupModel;
import edu.mx.unsis.unsiSmile.model.patients.GuardianModel;
import edu.mx.unsis.unsiSmile.model.patients.MaritalStatusModel;
import edu.mx.unsis.unsiSmile.model.patients.OccupationModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.patients.ReligionModel;

@Repository
public interface IPatientRepository extends JpaRepository<PatientModel, Long> {

    Optional<PatientModel> findByIdPatient(Long idPatient);

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

}
