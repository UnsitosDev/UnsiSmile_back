package edu.mx.unsis.unsiSmile.service.students;

import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.RegisterRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.common.ValidationUtils;
import edu.mx.unsis.unsiSmile.dtos.request.GenderRequest;
import edu.mx.unsis.unsiSmile.dtos.request.PersonRequest;
import edu.mx.unsis.unsiSmile.dtos.request.UserRequest;
import edu.mx.unsis.unsiSmile.dtos.request.students.StudentGroupRequest;
import edu.mx.unsis.unsiSmile.dtos.request.students.StudentRequest;
import edu.mx.unsis.unsiSmile.dtos.response.UserResponse;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.UserMapper;
import edu.mx.unsis.unsiSmile.mappers.students.GroupMapper;
import edu.mx.unsis.unsiSmile.mappers.students.StudentMapper;
import edu.mx.unsis.unsiSmile.model.PersonModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorGroupModel;
import edu.mx.unsis.unsiSmile.model.students.GroupModel;
import edu.mx.unsis.unsiSmile.model.students.StudentGroupModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.repository.IGenderRepository;
import edu.mx.unsis.unsiSmile.repository.IUserRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorGroupRepository;
import edu.mx.unsis.unsiSmile.repository.students.IStudentRepository;
import edu.mx.unsis.unsiSmile.service.UserService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PersonService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final IStudentRepository studentRepository;
    private final IGenderRepository genderRepository;
    private final StudentMapper studentMapper;
    private final UserMapper userMapper;
    private final PersonService personService;

    private final UserService userService;
    private final IUserRepository userRepository;
    private final GroupService groupService;
    private final StudentGroupService studentGroupService;
    private final GroupMapper groupMapper;
    private final ValidationUtils validationUtils;
    private final IProfessorGroupRepository professorGroupRepository;

    @Transactional
    public void createStudent(@NonNull StudentRequest request) {
        try {
            UserModel userModel = userService.createUser(
                    setCredentials(request.getEnrollment(), request.getPerson().getCurp())
            );

            List<String> invalidCurp = new ArrayList<>();
            PersonModel personModel = personService.createPersonEntity(request.getPerson(), invalidCurp);
            if(!invalidCurp.isEmpty()) {
                throw new AppException(invalidCurp.getFirst(), HttpStatus.BAD_REQUEST);
            }

            StudentModel studentModel = studentMapper.toEntity(request);

            studentModel.setUser(userModel);
            studentModel.setPerson(personModel);

            studentRepository.save(studentModel);
            studentGroupService.createStudentGroup(this.toSGRequest(request.getEnrollment(), request.getGroup().getId()));
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException("Failed to create student", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public StudentResponse getStudentByEnrollment(@NonNull String enrollment) {
        try {
            StudentModel studentModel = studentRepository.findById(enrollment)
                    .orElseThrow(() -> new AppException(ResponseMessages.STUDENT_NOT_FOUND + enrollment,
                            HttpStatus.NOT_FOUND));

            StudentResponse studentResponse = studentMapper.toDto(studentModel);

            StudentGroupModel studentGroup = studentGroupService.getStudentGroup(enrollment);
            if (studentGroup != null) {
                studentResponse.setGroup(groupMapper.toDto(studentGroup.getGroup()));
            }
            return studentResponse;
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException("Failed to fetch student", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public StudentResponse getStudentByUser(@NonNull UserRequest userRequest) {
        try {
            UserModel userModel = userMapper.toEntity(userRequest);

            StudentModel studentModel = studentRepository.findByUser(userModel)
                    .orElseThrow(() -> new AppException("Student not found with enrollment: " + userRequest,
                            HttpStatus.NOT_FOUND));
            return studentMapper.toDto(studentModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch student", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public Page<StudentResponse> getAllStudents(Pageable pageable, String keyword) {
        try {
            UserResponse currentUser = userService.getCurrentUser();

            Page<StudentGroupModel> studentGroupsPage;

            if (currentUser.getRole().getRole().equals(ERole.ROLE_PROFESSOR)) {
                List<ProfessorGroupModel> professorGroups = professorGroupRepository
                        .findByProfessorAndGroupStatus(currentUser.getUsername(), Constants.ACTIVE);

                if (professorGroups.isEmpty()) {
                    return Page.empty(pageable); // Sin grupos, sin estudiantes
                }

                Set<Long> idsGroups = professorGroups.stream()
                        .map(professorGroup -> professorGroup.getGroup().getIdGroup())
                        .collect(Collectors.toSet());

                studentGroupsPage = studentGroupService.getAllStudentGroupsByGroupIds(idsGroups, pageable, keyword);

            } else {
                studentGroupsPage = studentGroupService.getAllStudentsInGroups(pageable, keyword);
            }

            return studentGroupsPage.map(studentGroup -> {
                StudentModel student = studentGroup.getStudent();
                StudentResponse studentResponse = studentMapper.toDto(student);
                studentResponse.setGroup(groupMapper.toDto(studentGroup.getGroup()));
                return studentResponse;
            });

        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_STUDENTS, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }


    @Transactional
    public void updateStudent(@NonNull String enrollment, @NonNull StudentRequest updatedStudentRequest) {
        try {
            StudentModel studentModel = studentRepository.findById(enrollment)
                    .orElseThrow(() -> new AppException(ResponseMessages.STUDENT_NOT_FOUND + enrollment,
                            HttpStatus.NOT_FOUND));

            if (updatedStudentRequest.getPerson() != null) {
                PersonModel updatedPerson = personService.updatedPerson(
                        studentModel.getPerson().getCurp(), updatedStudentRequest.getPerson());
                studentModel.setPerson(updatedPerson);
            }

            if (updatedStudentRequest.getGroup() != null && updatedStudentRequest.getGroup().getId() != null) {
                studentGroupService.createStudentGroup(this.toSGRequest(enrollment,
                        updatedStudentRequest.getGroup().getId()));
            }

            studentRepository.save(studentModel);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_UPDATE_STUDENT, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteStudentByEnrollment(@NonNull String enrollment) {
        try {
            if (!studentRepository.existsById(enrollment)) {
                throw new AppException("Student not found with enrollment: " + enrollment, HttpStatus.NOT_FOUND);
            }
            studentRepository.deleteById(enrollment);
        } catch (EmptyResultDataAccessException ex) {
            throw new AppException("Student not found with enrollment: " + enrollment, HttpStatus.NOT_FOUND, ex);
        } catch (Exception ex) {
            throw new AppException("Failed to delete student", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private RegisterRequest setCredentials(String enrollment, String curp) {
        return RegisterRequest.builder()
                .password(curp)
                .username(enrollment)
                .role(ERole.ROLE_STUDENT.toString())
                .build();
    }

    @Transactional
    public List<String> loadStudentsFromFile(@NonNull MultipartFile file) {
        List<String> errors = new ArrayList<>();
        try (InputStream inputStream = file.getInputStream()) {
            String fileExtension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));

            List<List<String>> studentsData = new ArrayList<>();
            List<String> gruposData = new ArrayList<>();

            if (!fileExtension.equals("xlsx")) {
                throw new AppException("Unsupported file type. Only .xlsx files are supported.", HttpStatus.BAD_REQUEST);
            }

            try (Workbook workbook = new XSSFWorkbook(inputStream)) {
                Sheet sheet = workbook.getSheetAt(0);

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null || row.getPhysicalNumberOfCells() == 0) {
                        continue;
                    }
                    List<String> rowData = new ArrayList<>();
                    short validCellCounter = 0;

                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        Cell cell = row.getCell(j);
                        String cellValue = getCellValueAsString(cell);

                        if (cellValue != null && !cellValue.trim().isEmpty()) {
                            validCellCounter++;
                        }

                        rowData.add(cellValue);
                    }

                    if (validCellCounter > 6) {
                        String group = rowData.get(7).trim();
                        if (!group.isEmpty()) {
                            gruposData.add(group);
                        }

                        studentsData.add(rowData);
                    }
                }
            }

            studentRepository.disableAllStudents();
            userRepository.disableAllUsers(ERole.ROLE_STUDENT);

            Map<String, GroupModel> groups = groupService.saveGroups(gruposData);

            processAndSaveStudents(studentsData, groups, errors);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Error processing file", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
        return errors;
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return "";
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "UNSUPPORTED";
        }
    }

    private void processAndSaveStudents(List<List<String>> studentsData, Map<String, GroupModel> groups, List<String> errors) {
        for (List<String> studentRow : studentsData) {

            String enrollment = studentRow.get(3);

            String groupKey = studentRow.get(7);

            GroupModel groupModel = groups.get(groupKey);

            List<String> invalidCurps = new ArrayList<>();
            if (!validationUtils.validateCurpStructure(studentRow.get(4), invalidCurps)) {
                errors.add("CURP inválida para el alumno con matrícula: " + enrollment + ". Errores: " + String.join(", ", invalidCurps));
                continue;
            }

            StudentGroupRequest studentGroupRequest = toSGRequest(enrollment, groupModel.getIdGroup());

            Optional<StudentModel> existingStudent = studentRepository.findById(enrollment);

            if (existingStudent.isPresent()) {
                studentRepository.enableStudent(enrollment);
                userRepository.enableUserByStudentId(enrollment, ERole.ROLE_STUDENT);

                studentGroupService.createStudentGroup(studentGroupRequest);

                continue;
            }

            PersonModel personModel = personService.createPersonEntity(setPersonRequest(studentRow), invalidCurps);
            if (personModel == null) continue;

            UserModel userModel = userService.getUserModelByUsername(enrollment);
            if (userModel == null) {
                userModel = userService.createUser(this.setCredentials(enrollment, studentRow.get(4)));
            }

            StudentModel studentModel = StudentModel.builder()
                    .enrollment(enrollment)
                    .user(userModel)
                    .person(personModel)
                    .build();

            studentRepository.save(studentModel);

            studentGroupService.createStudentGroup(studentGroupRequest);
        }
    }

    private PersonRequest setPersonRequest(List<String> studentRow) {
        String[] names = splitFullName(studentRow.get(0));
        String curp = studentRow.get(4);

        return PersonRequest.builder()
                .curp(curp)
                .firstName(names[0])
                .secondName(names[1])
                .firstLastName(studentRow.get(1))
                .secondLastName(studentRow.get(2))
                .phone(studentRow.get(6))
                .email(studentRow.get(5))
                .birthDate(extractBirthDateFromCurp(curp))
                .gender(extractGenderFromCurp(curp))
                .build();
    }

    private String[] splitFullName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            return new String[]{"", ""};
        }

        String[] parts = fullName.trim().split("\\s+", 2);
        String firstName = parts[0];
        String secondName = parts.length > 1 ? parts[1] : "";

        return new String[]{firstName, secondName};
    }

    private LocalDate extractBirthDateFromCurp(String curp) {
        String birthDatePart = curp.substring(4, 10);
        int year = Integer.parseInt(birthDatePart.substring(0, 2));
        int month = Integer.parseInt(birthDatePart.substring(2, 4));
        int day = Integer.parseInt(birthDatePart.substring(4, 6));

        year += (year < 50) ? 2000 : 1900;

        return LocalDate.of(year, month, day);
    }

    private GenderRequest extractGenderFromCurp(String curp) {
        char genderChar = curp.charAt(10);
        String genderName = (genderChar == 'H') ? "Masculino" : "Femenino";

        return genderRepository.findByGender(genderName)
                .map(genderModel -> GenderRequest.builder()
                        .idGender(genderModel.getIdGender())
                        .gender(genderModel.getGender())
                        .build())
                .orElseThrow(() -> new AppException("Gender not found for CURP", HttpStatus.NOT_FOUND));
    }

    @Transactional
    public void updateStudentStatus(@NonNull String enrollment) {
        try {
            StudentModel studentModel = studentRepository.findById(enrollment).orElseThrow(()
                    -> new AppException("Student not found with enrollment: " + enrollment, HttpStatus.NOT_FOUND));

            studentModel.setStatusKey(Constants.ACTIVE.equals(studentModel.getStatusKey()) ? Constants.INACTIVE : Constants.ACTIVE);

            UserModel userModel = studentModel.getUser();
            userModel.setStatus(!userModel.isStatus());

            userRepository.save(userModel);
            studentRepository.save(studentModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException("Fail to update student status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private StudentGroupRequest toSGRequest(String enrollment, Long groupId) {
        return StudentGroupRequest.builder()
                .enrollment(enrollment)
                .groupId(groupId)
                .build();
    }

    @Transactional(readOnly = true)
    public StudentModel getStudentModel(String enrollment){
        UserResponse currentUser = userService.getCurrentUser();

        if (currentUser.getRole().getRole().equals(ERole.ROLE_STUDENT)) {
            return studentRepository.findById(currentUser.getUsername())
                    .orElseThrow(() -> new AppException(
                            ResponseMessages.STUDENT_NOT_FOUND,
                            HttpStatus.NOT_FOUND));
        } else {
            return studentRepository.findById(enrollment)
                    .orElseThrow(() -> new AppException(
                            ResponseMessages.STUDENT_NOT_FOUND,
                            HttpStatus.NOT_FOUND));
        }
    }
}
