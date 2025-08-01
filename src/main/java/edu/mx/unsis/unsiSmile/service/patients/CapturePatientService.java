package edu.mx.unsis.unsiSmile.service.patients;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.common.ValidationUtils;
import edu.mx.unsis.unsiSmile.dtos.request.patients.CapturePatientRequest;
import edu.mx.unsis.unsiSmile.dtos.request.patients.GuardianRequest;
import edu.mx.unsis.unsiSmile.dtos.request.patients.PatientRequest;
import edu.mx.unsis.unsiSmile.dtos.request.students.DigitizerPatientRequest;
import edu.mx.unsis.unsiSmile.dtos.response.UserResponse;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.patients.PatientMapper;
import edu.mx.unsis.unsiSmile.model.people.PersonModel;
import edu.mx.unsis.unsiSmile.model.addresses.AddressModel;
import edu.mx.unsis.unsiSmile.model.patients.GuardianModel;
import edu.mx.unsis.unsiSmile.model.patients.demographics.OccupationModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.digitizers.MedicalRecordDigitizerModel;
import edu.mx.unsis.unsiSmile.repository.patients.*;
import edu.mx.unsis.unsiSmile.service.UserService;
import edu.mx.unsis.unsiSmile.service.addresses.AddressService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PersonService;
import edu.mx.unsis.unsiSmile.service.students.DigitizerPatientService;
import edu.mx.unsis.unsiSmile.service.students.MedicalRecordDigitizerService;
import edu.mx.unsis.unsiSmile.service.students.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CapturePatientService {

    private final IPatientRepository patientRepository;
    private final INationalityRepository nationalityRepository;
    private final IMaritalStatusRepository maritalStatusRepository;
    private final IEthnicGroupRepository ethnicGroupRepository;
    private final IReligionRepository religionRepository;
    private final OccupationService occupationService;
    private final PatientMapper patientMapper;
    private final AddressService addressService;
    private final UserService userService;
    private final StudentService studentService;
    private final PersonService personService;
    private final ValidationUtils validationUtils;
    private final GuardianService guardianService;
    private final IGuardianRepository guardianRepository;
    private final MedicalRecordDigitizerService medicalRecordDigitizerService;
    private final DigitizerPatientService digitizerPatientService;

    @Transactional
    public void createPatient(@Valid @NonNull CapturePatientRequest patientRequest) {
        try {
            List<String> invalidCurp = new ArrayList<>();
            PersonModel personModel = personService.createPersonEntity(patientRequest.getPerson(), invalidCurp);
            if (!invalidCurp.isEmpty()) {
                throw new AppException(invalidCurp.getFirst(), HttpStatus.BAD_REQUEST);
            }

            Optional<PatientModel> existingPatient = patientRepository.findByPerson(personModel);

            if (existingPatient.isPresent()) {
                throw new AppException(ResponseMessages.PATIENT_ALREADY_EXISTS, HttpStatus.CONFLICT);
            }

            validatePatientRequest(patientRequest);

            PatientModel patientModel = preparePatientModel(patientRequest, personModel);
            validateAndSetGuardian(patientRequest, patientModel);
            AddressModel addressModel = addressService.findOrCreateAddress(patientRequest.getAddress());
            patientModel.setAddress(addressModel);
            PatientModel savedPatient = patientRepository.save(patientModel);

            relateDigitizerPatient(savedPatient);
        } catch (AppException e) {
            throw e;
        } catch (DataAccessException ex) {
            throw new AppException(ResponseMessages.ERROR_CREATING_PATIENT, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private void validatePatientRequest(PatientRequest patientRequest) {
        if (!nationalityRepository.existsById(patientRequest.getNationalityId())) {
            throw new AppException(ResponseMessages.NATIONALITY_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        if (!maritalStatusRepository.existsById(patientRequest.getMaritalStatus().getIdMaritalStatus())) {
            throw new AppException(ResponseMessages.MARITAL_STATUS_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        if (!ethnicGroupRepository.existsById(patientRequest.getEthnicGroup().getIdEthnicGroup())) {
            throw new AppException(ResponseMessages.ETHNIC_GROUP_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        if (!religionRepository.existsById(patientRequest.getReligion().getIdReligion())) {
            throw new AppException(ResponseMessages.RELIGION_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        if (patientRequest.getStudentEnrollment() != null && !patientRequest.getStudentEnrollment().isBlank()) {
             studentService.getStudentByEnrollment(patientRequest.getStudentEnrollment());
        }

        OccupationModel createdOccupation = occupationService.findOrCreateOccupation(patientRequest.getOccupation());
        patientRequest.getOccupation().setIdOccupation(createdOccupation.getIdOccupation());
    }

    private void validateAndSetGuardian(PatientRequest patientRequest, PatientModel patientModel) {
        boolean hasDisability = Optional.ofNullable(patientRequest.getHasDisability()).orElse(false);
        boolean hasGuardianRequest = patientRequest.getGuardian() != null;

        if (requiresGuardian(patientRequest) && !hasGuardianRequest) {
            throw new AppException(ResponseMessages.PATIENT_NEEDS_GUARDIAN, HttpStatus.BAD_REQUEST);
        }

        if (hasGuardianRequest && requiresGuardian(patientRequest)) {
            setGuardianForPatient(patientRequest.getGuardian(), patientModel);
        }

        if (hasGuardianRequest && hasDisability) {
            setGuardianForPatient(patientRequest.getGuardian(), patientModel);
        }
    }

    private boolean requiresGuardian(PatientRequest patientRequest) {
        return validationUtils.isMinor(patientRequest.getPerson().getBirthDate());
    }

    private void setGuardianForPatient(GuardianRequest guardianRequest, PatientModel patientModel) {
        String curp = guardianRequest.getPerson().getCurp();
        GuardianModel guardianModel = null;

        if (curp != null) {
            guardianModel = guardianRepository.findByPerson_CurpAndStatusKey(curp, "A").orElse(null);
        }

        if (guardianModel != null) {
            // Se asocia el guardian existente
            patientModel.setGuardian(guardianModel);
            return;
        }

        // Se crea un nuevo guardian y se asocia
        guardianModel = guardianService.createGuardianEntity(guardianRequest);
        patientModel.setGuardian(guardianModel);
    }

    private PatientModel preparePatientModel(CapturePatientRequest patientRequest, PersonModel person) {
        validateMedicalRecordNumberUniqueness(patientRequest.getMedicalRecordNumber());

        PatientModel patientModel = patientMapper.toEntity(patientRequest);

        patientModel.setMedicalRecordNumber(patientRequest.getMedicalRecordNumber());

        patientModel.setPerson(person);
        return patientModel;
    }

    private void relateDigitizerPatient(PatientModel savedPatient) {
        try {
            UserResponse user = userService.getCurrentUser();
            MedicalRecordDigitizerModel digitizerModel =
                    medicalRecordDigitizerService.getMedicalRecordDigitizerModelByUsername(user.getUsername());

            DigitizerPatientRequest digitizerPatientRequest = DigitizerPatientRequest.builder()
                    .patientId(savedPatient.getIdPatient())
                    .medicalRecordDigitizerId(digitizerModel.getIdMedicalRecordDigitizer())
                    .build();
            digitizerPatientService.createDigitizerPatient(digitizerPatientRequest);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.FAILED_TO_ASSIGN_PATIENT, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public PatientResponse updatePatient(@NonNull String idPatient, @NonNull CapturePatientRequest updatedPatientRequest) {
        try {
            Assert.notNull(updatedPatientRequest, "Updated PatientRequest cannot be null");

            PatientModel patientModel = getExistingPatient(idPatient);

            updatePatientData(patientModel, updatedPatientRequest);

            PatientModel updatedPatient = patientRepository.save(patientModel);

            return patientMapper.toDto(updatedPatient);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException("Failed to update patient", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private PatientModel getExistingPatient(@NonNull String idPatient) {
        return patientRepository.findByIdPatient(idPatient)
                .orElseThrow(() -> new AppException(ResponseMessages.PATIENT_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    private void updatePatientData(PatientModel patientModel, CapturePatientRequest updatedPatientRequest) {
        patientMapper.updateEntity(updatedPatientRequest, patientModel);

        if (updatedPatientRequest.getOccupation() != null) {
            OccupationModel occupationModel = occupationService
                    .findOrCreateOccupation(updatedPatientRequest.getOccupation());
            patientModel.setOccupation(occupationModel);
        }

        if (updatedPatientRequest.getPerson() != null) {
            PersonModel updatedPerson = personService.updatedPerson(
                    patientModel.getPerson().getCurp(), updatedPatientRequest.getPerson());
            patientModel.setPerson(updatedPerson);
        }

        if (updatedPatientRequest.getAddress() != null) {
            AddressModel addressModel = addressService.findOrCreateAddress(updatedPatientRequest.getAddress());
            patientModel.setAddress(addressModel);
        }

        Long newMedicalRecordNumber = updatedPatientRequest.getMedicalRecordNumber();
        Long currentMedicalRecordNumber = patientModel.getMedicalRecordNumber();

        if (newMedicalRecordNumber != null && !newMedicalRecordNumber.equals(currentMedicalRecordNumber)) {
            validateMedicalRecordNumberUniqueness(newMedicalRecordNumber);

            patientModel.setMedicalRecordNumber(newMedicalRecordNumber);
        }
    }

    private void validateMedicalRecordNumberUniqueness(@NonNull Long medicalRecordNumber) {
        Optional<PatientModel> existingPatient = patientRepository.findByMedicalRecordNumber(medicalRecordNumber);
        if (existingPatient.isPresent()) {
            throw new AppException(
                    String.format(ResponseMessages.MEDICAL_RECORD_NUMBER_ALREADY_EXISTS, medicalRecordNumber),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}