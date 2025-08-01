package edu.mx.unsis.unsiSmile.service.patients;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.common.ValidationUtils;
import edu.mx.unsis.unsiSmile.dtos.request.users.UserRequest;
import edu.mx.unsis.unsiSmile.dtos.request.patients.GuardianRequest;
import edu.mx.unsis.unsiSmile.dtos.request.patients.PatientRequest;
import edu.mx.unsis.unsiSmile.dtos.request.students.StudentPatientRequest;
import edu.mx.unsis.unsiSmile.dtos.response.users.UserResponse;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientResponse;
import edu.mx.unsis.unsiSmile.dtos.response.students.PatientStudentResponse;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentPatientResponse;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.patients.PatientMapper;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentRes;
import edu.mx.unsis.unsiSmile.model.people.PersonModel;
import edu.mx.unsis.unsiSmile.model.addresses.AddressModel;
import edu.mx.unsis.unsiSmile.model.enums.ReviewStatus;
import edu.mx.unsis.unsiSmile.model.patients.GuardianModel;
import edu.mx.unsis.unsiSmile.model.patients.demographics.OccupationModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.repository.patients.demographics.IEthnicGroupRepository;
import edu.mx.unsis.unsiSmile.repository.patients.demographics.IMaritalStatusRepository;
import edu.mx.unsis.unsiSmile.repository.patients.demographics.INationalityRepository;
import edu.mx.unsis.unsiSmile.repository.patients.demographics.IReligionRepository;
import edu.mx.unsis.unsiSmile.repository.treatments.ITreatmentDetailRepository;
import edu.mx.unsis.unsiSmile.repository.patients.*;
import edu.mx.unsis.unsiSmile.service.UserService;
import edu.mx.unsis.unsiSmile.service.addresses.AddressService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PersonService;
import edu.mx.unsis.unsiSmile.service.students.StudentPatientService;
import edu.mx.unsis.unsiSmile.service.students.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final IPatientRepository patientRepository;
    private final INationalityRepository nationalityRepository;
    private final IMaritalStatusRepository maritalStatusRepository;
    private final IEthnicGroupRepository ethnicGroupRepository;
    private final IReligionRepository religionRepository;
    private final ITreatmentDetailRepository treatmentDetailRepository;
    private final OccupationService occupationService;
    private final PatientMapper patientMapper;
    private final AddressService addressService;
    private final UserService userService;
    private final StudentPatientService studentPatientService;
    private final StudentService studentService;
    private final PersonService personService;
    private final ValidationUtils validationUtils;
    private final GuardianService guardianService;
    private final IGuardianRepository guardianRepository;
    @Value("${max.medical.record.number}")
    private int maxFileNumberProperties;

    @Transactional
    public void createPatient(@Valid @NonNull PatientRequest patientRequest) {
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

            String enrollment = patientRequest.getStudentEnrollment();
            if (enrollment != null && !enrollment.isBlank()) {
                StudentPatientRequest studentPatientRequest = StudentPatientRequest.builder()
                        .patientId(savedPatient.getIdPatient())
                        .studentEnrollment(patientRequest.getStudentEnrollment())
                        .build();

                studentPatientService.createStudentPatient(studentPatientRequest);
            } else {
                relateStudentPatient(savedPatient);
            }
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
            return;
        }

        if (hasGuardianRequest && hasDisability) {
            setGuardianForPatient(patientRequest.getGuardian(), patientModel);
            return;
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

    private PatientModel preparePatientModel(PatientRequest patientRequest, PersonModel person) {
        long maxMedicalRecordNumber = Optional.ofNullable(patientRepository.findMaxFileNumber()).orElse(0L);

        PatientModel patientModel = patientMapper.toEntity(patientRequest);

        if (maxMedicalRecordNumber < maxFileNumberProperties) {
            patientModel.setMedicalRecordNumber((long) maxFileNumberProperties);
        } else {
            patientModel.setMedicalRecordNumber(maxMedicalRecordNumber + 1);
        }

        patientModel.setPerson(person);
        return patientModel;
    }

    private void relateStudentPatient(PatientModel savedPatient) {

        UserResponse user = userService.getCurrentUser();

        if (!user.getRole().getRole().equals(ERole.ROLE_STUDENT)) {
            return;
        }

        try {
            StudentResponse student = studentService.getStudentByUser(buildUserRequest(user));

            StudentPatientRequest studentPatientRequest = StudentPatientRequest.builder()
                    .patientId(savedPatient.getIdPatient())
                    .studentEnrollment(student.getEnrollment())
                    .build();

            studentPatientService.createStudentPatient(studentPatientRequest);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.FAILED_TO_ASSIGN_PATIENT, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public Page<PatientResponse> getAllPatients(Pageable pageable, String keyword) {
        try {
            UserResponse user = userService.getCurrentUser();
            if (user.getRole().getRole() == ERole.ROLE_STUDENT) {
                return getPatientsForStudent(user, pageable, keyword);
            } else {
                return getAllPatientsPage(pageable, keyword);
            }
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.PATIENT_FETCH_FAILED, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private Page<PatientResponse> getPatientsForStudent(UserResponse user, Pageable pageable, String keyword) {
        StudentResponse studentResponse = studentService.getStudentByUser(buildUserRequest(user));
        List<PatientModel> patients = getPatientsByStudents(studentResponse, keyword, pageable);
        return new PageImpl<>(patientsMapped(patients), pageable, patients.size());
    }

    private Page<PatientResponse> getAllPatientsPage(Pageable pageable, String keyword) {
        Page<PatientModel> allPatients;
        if (keyword == null || keyword.isEmpty()) {
            allPatients = patientRepository.findAll(pageable);
        } else {
            allPatients = patientRepository.findAllBySearchInput(keyword, pageable);
        }
        Set<String> patientIds = extractPatientIds(allPatients);

        List<PatientStudentResponse> studentPatientResponses = studentPatientService.getByPatients(patientIds);
        Map<String, StudentRes> studentMap = createStudentMap(studentPatientResponses);

        List<PatientResponse> patientResponses = mapPatientsToResponses(allPatients, studentMap);

        return new PageImpl<>(patientResponses, pageable, allPatients.getTotalElements());
    }

    private Set<String> extractPatientIds(Page<PatientModel> allPatients) {
        Set<String> patientIds = new HashSet<>();
        allPatients.forEach(patient -> patientIds.add(patient.getIdPatient()));
        return patientIds;
    }

    private Map<String, StudentRes> createStudentMap(List<PatientStudentResponse> studentPatientResponses) {
        Map<String, StudentRes> studentMap = new HashMap<>();
        for (PatientStudentResponse studentPatientResponse : studentPatientResponses) {
            String patientId = studentPatientResponse.getPatientId();
            StudentRes student = studentPatientResponse.getStudent();
            studentMap.put(patientId, student);
        }
        return studentMap;
    }

    private List<PatientResponse> mapPatientsToResponses(Page<PatientModel> allPatients,
            Map<String, StudentRes> studentMap) {
        return allPatients.stream()
                .map(patient -> {
                    PatientResponse patientResponse = patientMapper.toDto(patient);
                    StudentRes studentForPatient = studentMap.get(patientResponse.getIdPatient());
                    if (studentForPatient != null) {
                        patientResponse.setStudent(studentForPatient);
                    }
                    return patientResponse;
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public PatientResponse getPatientById(@NonNull String idPatient) {
        try {
            PatientModel patientModel = patientRepository.findByIdPatient(idPatient)
                    .orElseThrow(() -> new AppException(ResponseMessages.PATIENT_NOT_FOUND, HttpStatus.NOT_FOUND));
            UserResponse user = userService.getCurrentUser();
            PatientResponse response;
            if (user.getRole().getRole() == ERole.ROLE_STUDENT) {
                StudentResponse studentResponse = studentService.getStudentByUser(buildUserRequest(user));
                List<PatientModel> patients = getPatientsByStudents(studentResponse, null, null);
                response = getPatientByIdByStudent(patients, idPatient);
            } else {
                response = patientMapper.toDto(patientModel);
            }

            boolean hasInProgress = treatmentDetailRepository
                    .existsByPatientMedicalRecord_Patient_idPatientAndStatus(
                            idPatient,
                            ReviewStatus.IN_PROGRESS
                    );
            response.setHasTreatmentInProgress(hasInProgress);

            return response;
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_PATIENT_WITH_ID, HttpStatus.INTERNAL_SERVER_ERROR,
                    ex);
        }
    }

    @Transactional
    public PatientResponse updatePatient(@NonNull String idPatient, @NonNull PatientRequest updatedPatientRequest) {
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

    @Transactional
    public void deletePatientById(@NonNull String idPatient) {
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

    @Transactional(readOnly = true)
    public PatientModel getPatientModel(@NonNull String id) {
        try {
            return patientRepository.findByIdPatient(id)
                    .orElseThrow(
                            () -> new AppException(ResponseMessages.PATIENT_NOT_FOUND, HttpStatus.NOT_FOUND));
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException("Failed to fetch patient by ID", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private UserRequest buildUserRequest(UserResponse user) {
        return UserRequest.builder()
                .idUser(user.getId())
                .build();
    }

    private List<PatientModel> getPatientsByStudents(StudentResponse studentResponse, String keyword,
            Pageable pageable) {
        List<StudentPatientResponse> studentPatientResponses = studentPatientService
                .getAllStudentPatients(studentResponse.getEnrollment(), keyword, pageable);

        Set<String> patientIds = studentPatientResponses.stream()
                .map(studentPatientResponse -> studentPatientResponse.getPatient().getIdPatient())
                .collect(Collectors.toSet());
        return patientRepository.findAllById(patientIds);
    }

    private List<PatientResponse> patientsMapped(List<PatientModel> patientes) {
        if (patientes.isEmpty()) {
            return Collections.emptyList();
        }
        return patientes.stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

    private PatientResponse getPatientByIdByStudent(List<PatientModel> patients, String idPatient) {
        Optional<PatientModel> optionalPatient = patients.stream()
                .filter(patient -> patient.getIdPatient().equals(idPatient))
                .findFirst();

        if (optionalPatient.isPresent()) {
            return patientMapper.toDto(optionalPatient.get());
        } else {
            throw new AppException(ResponseMessages.WITHOUT_PERMITS_FOR_GET_PATIENT, HttpStatus.FORBIDDEN);
        }
    }

    private PatientModel getExistingPatient(@NonNull String idPatient) {
        return patientRepository.findByIdPatient(idPatient)
                .orElseThrow(() -> new AppException(ResponseMessages.PATIENT_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    private void updatePatientData(PatientModel patientModel, PatientRequest updatedPatientRequest) {
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
    }

    @Transactional(readOnly = true)
    public PatientResponse getPatientByCurp(@NonNull String curp) {
        try {
            PatientModel patientModel = patientRepository.findByCurp(curp)
                    .orElseThrow(() -> new AppException(ResponseMessages.PATIENT_NOT_FOUND, HttpStatus.NOT_FOUND));
            return patientMapper.toDto(patientModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.PATIENT_FETCH_FAILED, HttpStatus.NOT_FOUND, ex);
        }
    }
}
