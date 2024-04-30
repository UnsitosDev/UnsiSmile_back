package edu.mx.unsis.unsiSmile.service.patients;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.PersonRequest;
import edu.mx.unsis.unsiSmile.dtos.request.addresses.AddressRequest;
import edu.mx.unsis.unsiSmile.dtos.request.patients.GuardianRequest;
import edu.mx.unsis.unsiSmile.dtos.request.patients.PatientRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.PersonMapper;
import edu.mx.unsis.unsiSmile.mappers.addresses.AddressMapper;
import edu.mx.unsis.unsiSmile.mappers.patients.GuardianMapper;
import edu.mx.unsis.unsiSmile.mappers.patients.PatientMapper;
import edu.mx.unsis.unsiSmile.model.PersonModel;
import edu.mx.unsis.unsiSmile.model.addresses.AddressModel;
import edu.mx.unsis.unsiSmile.model.patients.GuardianModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.repository.IPersonRepository;
import edu.mx.unsis.unsiSmile.repository.addresses.IAddressRepository;
import edu.mx.unsis.unsiSmile.repository.patients.IGuardianRepository;
import edu.mx.unsis.unsiSmile.repository.patients.IPatientRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final IPatientRepository patientRepository;
    private final IPersonRepository personRepository;
    private final PatientMapper patientMapper;
    private final PersonMapper personMapper;
    private final IGuardianRepository guardianRepository;
    private final GuardianMapper guardianMapper;
    private final IAddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Transactional
    public PatientResponse createPatient(@Valid @NonNull PatientRequest patientRequest) {
        try {
            // Create person entity
            PersonModel person = createPersonEntity(patientRequest.getPerson());

            // Map the DTO request to the patient entity
            PatientModel patientModel = patientMapper.toEntity(patientRequest);
            patientModel.setPerson(person);

            // Create a guardian if the patient is a minor
            if (patientRequest.getIsMinor() && patientRequest.getGuardian() != null) {
                GuardianModel guardianModel = createGuardianEntity(patientRequest.getGuardian());
                patientModel.setGuardian(guardianModel);
            }

            //create the address
            AddressModel addressModel = createAddressModel(patientRequest.getAddress());
            patientModel.setAddress(addressModel);

            // Save the entity to the database
            PatientModel savedPatient = patientRepository.save(patientModel);

            // Map the saved entity back to a response DTO
            return patientMapper.toDto(savedPatient);
        } catch (DataAccessException ex) {
            throw new AppException("Failed to create patient", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    // Method to create a address entity
    private AddressModel createAddressModel(AddressRequest addressRequest){
        Assert.notNull(addressRequest, "AddressRequest cannot be null");
        return addressRepository.save(addressMapper.toEntity(addressRequest));
    }

    // Method to create a person entity
    private PersonModel createPersonEntity(PersonRequest personRequest) {
        Assert.notNull(personRequest, "PersonRequest cannot be null");
        return personRepository.save(personMapper.toEntity(personRequest));
    }

    // Method to create a guardian entity
    private GuardianModel createGuardianEntity(GuardianRequest guardianRequest) {
        Assert.notNull(guardianRequest, "GuardianRequest cannot be null");
        return guardianRepository.save(guardianMapper.toEntity(guardianRequest));
    }

    @Transactional(readOnly = true)
    public List<PatientResponse> getAllPatients() {
        try {
            List<PatientModel> allPatients = patientRepository.findAll();
            return allPatients.stream()
                    .map(patientMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch patient by ID", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public PatientResponse getPatientById(@NonNull Long idPatient) {
        try {
            PatientModel patientModel = patientRepository.findByIdPatient(idPatient)
                    .orElseThrow(
                            () -> new AppException("Patient not found with ID: " + idPatient, HttpStatus.NOT_FOUND));

            return patientMapper.toDto(patientModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch patient by ID", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<PatientResponse> getPatientsByAdmissionDate(@NonNull LocalDate admissionDate) {
        try {
            List<PatientModel> patients = patientRepository.findByAdmissionDate(admissionDate);
            return patients.stream()
                    .map(patientMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch patients by admission date", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<PatientResponse> getPatientsByIsMinor(@NonNull Boolean isMinor) {
        try {
            List<PatientModel> patients = patientRepository.findByIsMinor(isMinor);
            return patients.stream()
                    .map(patientMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch patients by minor status", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<PatientResponse> getPatientsByHasDisability(@NonNull Boolean hasDisability) {
        try {
            List<PatientModel> patients = patientRepository.findByHasDisability(hasDisability);
            return patients.stream()
                    .map(patientMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch patients by disability status", HttpStatus.INTERNAL_SERVER_ERROR,
                    ex);
        }
    }

    // Implement similar methods for other search criteria like nationality, person,
    // address, marital status, occupation, ethnic group, religion, guardian, etc.

    @Transactional
    public PatientResponse updatePatient(@NonNull Long idPatient, @NonNull PatientRequest updatedPatientRequest) {
        try {
            Assert.notNull(updatedPatientRequest, "Updated PatientRequest cannot be null");

            // Find the patient in the database
            PatientModel patientModel = patientRepository.findByIdPatient(idPatient)
                    .orElseThrow(
                            () -> new AppException("Patient not found with ID: " + idPatient, HttpStatus.NOT_FOUND));

            // Update the patient entity with the new data
            patientMapper.updateEntity(updatedPatientRequest, patientModel);

            // Save the updated entity
            PatientModel updatedPatient = patientRepository.save(patientModel);

            // Map the updated entity back to a response DTO
            return patientMapper.toDto(updatedPatient);
        } catch (Exception ex) {
            throw new AppException("Failed to update patient", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deletePatientById(@NonNull Long idPatient) {
        try {
            // Check if the patient exists
            if (!patientRepository.existsById(idPatient)) {
                throw new AppException("Patient not found with ID: " + idPatient, HttpStatus.NOT_FOUND);
            }
            patientRepository.deleteById(idPatient);
        } catch (Exception ex) {
            throw new AppException("Failed to delete patient", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
