package edu.mx.unsis.unsiSmile.service.students;

import edu.mx.unsis.unsiSmile.authenticationProviders.dtos.RegisterRequest;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.authenticationProviders.model.UserModel;
import edu.mx.unsis.unsiSmile.dtos.request.GenderRequest;
import edu.mx.unsis.unsiSmile.dtos.request.PersonRequest;
import edu.mx.unsis.unsiSmile.dtos.request.UserRequest;
import edu.mx.unsis.unsiSmile.dtos.request.students.StudentRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.StudentResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.UserMapper;
import edu.mx.unsis.unsiSmile.mappers.students.StudentMapper;
import edu.mx.unsis.unsiSmile.model.PersonModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.repository.IGenderRepository;
import edu.mx.unsis.unsiSmile.repository.IUserRepository;
import edu.mx.unsis.unsiSmile.repository.students.IStudentRepository;
import edu.mx.unsis.unsiSmile.service.UserService;
import edu.mx.unsis.unsiSmile.service.medicalHistories.PersonService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    @Transactional
    public void createStudent(StudentRequest request) {
        try {
            UserModel userModel = userService.createUser(
                    setCredentials(request.getEnrollment(), request.getPerson().getCurp())
            );

            PersonModel personModel = personService.createPersonEntity(request.getPerson());

            StudentModel studentModel = studentMapper.toEntity(request);

            studentModel.setUser(userModel);
            studentModel.setPerson(personModel);

            studentRepository.save(studentModel);
        } catch (Exception ex) {
            throw new AppException("Failed to create student", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public StudentResponse getStudentByEnrollment(String enrollment) {
        try {
            StudentModel studentModel = studentRepository.findById(enrollment)
                    .orElseThrow(() -> new AppException("Student not found with enrollment: " + enrollment,
                            HttpStatus.NOT_FOUND));
            return studentMapper.toDto(studentModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch student", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public StudentResponse getStudentByUser(UserRequest userRequest) {
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
            Page<StudentModel> allStudents;

            if (keyword == null || keyword.isEmpty()) {
                allStudents = studentRepository.findAll(pageable);
            } else {
                Integer keywordInt = null;
                if (keyword.matches("\\d+")) {
                    keywordInt = Integer.parseInt(keyword);
                } else if (!keyword.matches("[a-zA-Z]+")) {
                    throw new AppException("The input is not valid. It must be a number or a string.",
                    HttpStatus.BAD_REQUEST);
                }

                allStudents = studentRepository.findAllBySearchInput(keyword, keywordInt, pageable);
            }

            return allStudents.map(studentMapper::toDto);
        } catch (AppException ex) {
          throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to fetch students", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void updateStudent(String enrollment, StudentRequest updatedStudentRequest) {
        try {
            StudentModel studentModel = studentRepository.findById(enrollment)
                    .orElseThrow(() -> new AppException("Student not found with enrollment: " + enrollment,
                            HttpStatus.NOT_FOUND));

            studentMapper.updateEntity(updatedStudentRequest, studentModel);

            studentRepository.save(studentModel);
        } catch (Exception ex) {
            throw new AppException("Failed to update student", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteStudentByEnrollment(String enrollment) {
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
    public void loadStudentsFromFile(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            String fileExtension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));

            List<List<String>> studentsData = new ArrayList<>();

            if (!fileExtension.equals("xlsx")) {
                throw new AppException("Unsupported file type. Only .xlsx files are supported.", HttpStatus.BAD_REQUEST);
            }

            try (Workbook workbook = new XSSFWorkbook(inputStream)) {
                Sheet sheet = workbook.getSheetAt(0);

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) continue;

                    List<String> rowData = new ArrayList<>();
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        Cell cell = row.getCell(j);
                        rowData.add(getCellValueAsString(cell));
                    }

                    studentsData.add(rowData);
                }
            }

            studentRepository.disableAllStudents();
            userRepository.disableAllUsers(ERole.ROLE_STUDENT);

            processAndSaveStudents(studentsData);

        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Error processing file", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
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

    private void processAndSaveStudents(List<List<String>> studentsData) {
        for (List<String> studentRow : studentsData) {
            String enrollment = studentRow.get(3);

            Optional<StudentModel> existingStudent = studentRepository.findById(enrollment);

            if (existingStudent.isPresent()) {
                studentRepository.enableStudent(enrollment);
                userRepository.enableUserByStudentId(enrollment, ERole.ROLE_STUDENT);
                continue;
            }

            UserModel userModel = userService.createUser(
                    this.setCredentials(enrollment, studentRow.get(4))
            );

            PersonModel personModel = personService.createPersonEntity(setPersonRequest(studentRow));

            StudentModel studentModel = StudentModel.builder()
                    .enrollment(enrollment)
                    .user(userModel)
                    .person(personModel)
                    .build();

            studentRepository.save(studentModel);
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
}
