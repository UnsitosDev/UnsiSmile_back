package edu.mx.unsis.unsiSmile.service.patients;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.dtos.request.PersonRequest;
import edu.mx.unsis.unsiSmile.dtos.request.UserRequest;
import edu.mx.unsis.unsiSmile.dtos.request.addresses.AddressRequest;
import edu.mx.unsis.unsiSmile.dtos.request.patients.GuardianRequest;
import edu.mx.unsis.unsiSmile.dtos.request.patients.PatientRequest;
import edu.mx.unsis.unsiSmile.dtos.request.students.StudentPatientRequest;
import edu.mx.unsis.unsiSmile.dtos.response.PersonResponse;
import edu.mx.unsis.unsiSmile.dtos.response.UserResponse;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientResponse;
import edu.mx.unsis.unsiSmile.dtos.response.students.PatientStudentResponse;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentPatientResponse;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.PersonMapper;
import edu.mx.unsis.unsiSmile.mappers.addresses.AddressMapper;
import edu.mx.unsis.unsiSmile.mappers.patients.GuardianMapper;
import edu.mx.unsis.unsiSmile.mappers.patients.PatientMapper;
import edu.mx.unsis.unsiSmile.mappers.students.StudentRes;
import edu.mx.unsis.unsiSmile.model.PersonModel;
import edu.mx.unsis.unsiSmile.model.addresses.AddressModel;
import edu.mx.unsis.unsiSmile.model.patients.GuardianModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.repository.addresses.IAddressRepository;
import edu.mx.unsis.unsiSmile.repository.patients.IGuardianRepository;
import edu.mx.unsis.unsiSmile.repository.patients.IPatientRepository;
import edu.mx.unsis.unsiSmile.service.UserService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PersonService;
import edu.mx.unsis.unsiSmile.service.students.StudentPatientService;
import edu.mx.unsis.unsiSmile.service.students.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final IPatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final PersonMapper personMapper;
    private final IGuardianRepository guardianRepository;
    private final GuardianMapper guardianMapper;
    private final IAddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final UserService userService;
    private final StudentPatientService studentPatientService;
    private final StudentService studentService;
    private final PersonService personService;

    @Transactional
    public void createPatient(@Valid @NonNull PatientRequest patientRequest) {
        try {

            PersonModel person = createPersonEntity(patientRequest.getPerson());
            PatientModel patientModel = preparePatientModel(patientRequest, person);
            validateAndSetGuardian(patientRequest, patientModel);
            AddressModel addressModel = createAddressModel(patientRequest.getAddress());
            patientModel.setAddress(addressModel);
            PatientModel savedPatient = patientRepository.save(patientModel);
            relateStudentPatient(savedPatient, patientRequest.getStudent().getEnrollment());
        } catch (DataAccessException ex) {
            throw new AppException("Failed to create patient", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private void validateAndSetGuardian(PatientRequest patientRequest, PatientModel patientModel) {
        if (patientRequest.getIsMinor() || isMinor(patientRequest.getPerson().getBirthDate())) {
            GuardianModel guardianModel = Optional.ofNullable(patientRequest.getGuardian())
                    .map(this::createGuardianEntity)
                    .orElseThrow(() -> new AppException("The patient needs to have a guardian", HttpStatus.BAD_REQUEST));
            patientModel.setGuardian(guardianModel);
        }
    }

    private boolean isMinor(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        return Period.between(birthDate, today).getYears() < 18;
    }

    private PatientModel preparePatientModel(PatientRequest patientRequest, PersonModel person) {
        PatientModel patientModel = patientMapper.toEntity(patientRequest);
        patientModel.setPerson(person);
        return patientModel;
    }

    private void relateStudentPatient(PatientModel savedPatient, String enrollment) {

        // search the user
        UserResponse user = userService.getCurrentUser();
        StudentPatientRequest studentPatientRequest;
        StudentResponse student;
        try {

            student = user.getRole().getRole().equals(ERole.ROLE_STUDENT)
                    ? studentService.getStudentByUser(buildUserRequest(user))
                    : studentService.getStudentByEnrollment(enrollment);

            studentPatientRequest = StudentPatientRequest.builder()
                    .patientId(savedPatient.getIdPatient())
                    .studentEnrollment(student.getEnrollment())
                    .build();

            studentPatientService.createStudentPatient(studentPatientRequest);
        } catch (AppException ex) {
           throw ex;
        } catch (Exception e) {
            throw new AppException("Failed to assign student", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    // Method to create an address entity
    private AddressModel createAddressModel(AddressRequest addressRequest) {
        Assert.notNull(addressRequest, "AddressRequest cannot be null");
        return addressRepository.save(addressMapper.toEntity(addressRequest));
    }

    // Method to create a person entity
    private PersonModel createPersonEntity(PersonRequest personRequest) {
        Assert.notNull(personRequest, "PersonRequest cannot be null");
        PersonResponse personResponse = personService.createPerson(personRequest);
        return personMapper.toEntity(personResponse);
    }

    // Method to create a guardian entity
    private GuardianModel createGuardianEntity(GuardianRequest guardianRequest) {
        Assert.notNull(guardianRequest, "GuardianRequest cannot be null");
        return guardianRepository.save(guardianMapper.toEntity(guardianRequest));
    }

    @Transactional(readOnly = true)
    public Page<PatientResponse> getAllPatients(Pageable pageable) {
        try {
            UserResponse user = userService.getCurrentUser();
            if (user.getRole().getRole() == ERole.ROLE_STUDENT) {
                return getPatientsForStudent(user, pageable);
            } else {
                return getAllPatientsPage(pageable);
            }
        } catch (Exception ex) {
            throw new AppException("Failed to fetch patients", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private Page<PatientResponse> getPatientsForStudent(UserResponse user, Pageable pageable) {
        StudentResponse studentResponse = studentService.getStudentByUser(buildUserRequest(user));
        List<PatientModel> patients = getPatientsByStudents(studentResponse);
        return new PageImpl<>(patientsMapped(patients), pageable, patients.size());
    }

    private Page<PatientResponse> getAllPatientsPage(Pageable pageable) {
        Page<PatientModel> allPatients = patientRepository.findAll(pageable);
        Set<Long> patientIds = extractPatientIds(allPatients);

        List<PatientStudentResponse> studentPatientResponses = studentPatientService.getByPatients(patientIds);
        Map<Long, StudentRes> studentMap = createStudentMap(studentPatientResponses);

        List<PatientResponse> patientResponses = mapPatientsToResponses(allPatients, studentMap);

        return new PageImpl<>(patientResponses, pageable, allPatients.getTotalElements());
    }

    private Set<Long> extractPatientIds(Page<PatientModel> allPatients) {
        Set<Long> patientIds = new HashSet<>();
        allPatients.forEach(patient -> patientIds.add(patient.getIdPatient()));
        return patientIds;
    }

    private Map<Long, StudentRes> createStudentMap(List<PatientStudentResponse> studentPatientResponses) {
        Map<Long, StudentRes> studentMap = new HashMap<>();
        for (PatientStudentResponse studentPatientResponse : studentPatientResponses) {
            Long patientId = studentPatientResponse.getPatientId();
            StudentRes student = studentPatientResponse.getStudent();
            studentMap.put(patientId, student);
        }
        return studentMap;
    }

    private List<PatientResponse> mapPatientsToResponses(Page<PatientModel> allPatients, Map<Long, StudentRes> studentMap) {
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
    public PatientResponse getPatientById(@NonNull Long idPatient) {
        try {
            UserResponse user = userService.getCurrentUser();
            if (user.getRole().getRole() == ERole.ROLE_STUDENT) {
                StudentResponse studentResponse = studentService.getStudentByUser(buildUserRequest(user));
                List<PatientModel> patients = getPatientsByStudents(studentResponse);
                return getPatientByIdByStudent(patients, idPatient);
            } else {
                PatientModel patientModel = getPatientModel(idPatient);
                return patientMapper.toDto(patientModel);
            }

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

    @Transactional(readOnly = true)
    public PatientModel getPatientModel(@NonNull Long id) {
        try {
            return patientRepository.findByIdPatient(id)
                    .orElseThrow(
                            () -> new AppException("Patient not found with ID: " + id, HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new AppException("Failed to fetch patient by ID", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private UserRequest buildUserRequest(UserResponse user) {
        return UserRequest.builder()
                .idUser(user.getId())
                .build();
    }

    private List<PatientModel> getPatientsByStudents(StudentResponse studentResponse) {
        List<StudentPatientResponse> studentPatientResponses = studentPatientService
                .getAllStudentPatients(studentResponse.getEnrollment());

        Set<Long> patientIds = studentPatientResponses.stream()
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

    private PatientResponse getPatientByIdByStudent(List<PatientModel> patients, Long idPatient) {
        Optional<PatientModel> optionalPatient = patients.stream()
                .filter(patient -> patient.getIdPatient().equals(idPatient))
                .findFirst();

        if (optionalPatient.isPresent()) {
            return patientMapper.toDto(optionalPatient.get());
        } else {
            throw new AppException("Student does not have access to this patient", HttpStatus.FORBIDDEN);
        }
    }
}
