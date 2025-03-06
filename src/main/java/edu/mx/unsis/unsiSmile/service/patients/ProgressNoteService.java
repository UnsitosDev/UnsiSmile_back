package edu.mx.unsis.unsiSmile.service.patients;

import edu.mx.unsis.unsiSmile.authenticationProviders.model.ERole;
import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.common.ValidationUtils;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ProgressNoteRequest;
import edu.mx.unsis.unsiSmile.dtos.response.UserResponse;
import edu.mx.unsis.unsiSmile.dtos.response.patients.ProgressNoteFileResponse;
import edu.mx.unsis.unsiSmile.dtos.response.patients.ProgressNoteResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.patients.ProgressNoteFileMapper;
import edu.mx.unsis.unsiSmile.mappers.patients.ProgressNoteMapper;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.patients.ProgressNoteFileModel;
import edu.mx.unsis.unsiSmile.model.patients.ProgressNoteModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorClinicalAreaModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import edu.mx.unsis.unsiSmile.model.students.StudentModel;
import edu.mx.unsis.unsiSmile.repository.patients.IPatientRepository;
import edu.mx.unsis.unsiSmile.repository.patients.IProgressNoteFileRepository;
import edu.mx.unsis.unsiSmile.repository.patients.IProgressNoteRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorClinicalAreaRepository;
import edu.mx.unsis.unsiSmile.repository.professors.IProfessorRepository;
import edu.mx.unsis.unsiSmile.repository.students.IStudentRepository;
import edu.mx.unsis.unsiSmile.service.UserService;
import edu.mx.unsis.unsiSmile.service.files.FileStorageService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgressNoteService {
    private final IProgressNoteRepository progressNoteRepository;
    private final IProgressNoteFileRepository progressNoteFileRepository;
    private final IPatientRepository patientRepository;
    private final IStudentRepository studentRepository;
    private final IProfessorRepository professorRepository;
    private final IProfessorClinicalAreaRepository professorClinicalAreaRepository;
    private final ProgressNoteMapper progressNoteMapper;
    private final FileStorageService fileStorageService;
    private final UserService userService;
    private final ProgressNoteFileMapper progressNoteFileMapper;
    private final ValidationUtils validationUtils;

    @Transactional
    public ProgressNoteResponse createProgressNote(@NonNull ProgressNoteRequest request) {
        try {
            Assert.notNull(request, ResponseMessages.REQUEST_CANNOT_BE_NULL);

            PatientModel patient = patientRepository.findById(request.getPatientId())
                    .orElseThrow(() -> new AppException(ResponseMessages.PATIENT_NOT_FOUND + " con ID: "
                            + request.getPatientId(), HttpStatus.NOT_FOUND));

            UserResponse user = userService.getCurrentUser();

            StudentModel student = studentRepository.findById(user.getUsername())
                    .orElseThrow(() -> new AppException(ResponseMessages.STUDENT_NOT_FOUND + " con ID: "
                            + user.getUsername(), HttpStatus.NOT_FOUND));

            ProfessorClinicalAreaModel professorClinicalArea = professorClinicalAreaRepository.findById(
                    request.getProfessorClinicalAreaId())
                    .orElseThrow(() -> new AppException(ResponseMessages.PROFESSOR_CLINICAL_AREA_NOT_FOUND
                            + " con ID: " + request.getProfessorClinicalAreaId(), HttpStatus.NOT_FOUND));

            ProgressNoteModel progressNote = progressNoteMapper.toEntity(request);

            progressNote.setPatient(patient);
            progressNote.setProfessor(professorClinicalArea.getProfessor());

            return progressNoteMapper.toDto(progressNoteRepository.save(progressNote));
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_CREATING_PROGRESS_NOTE + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void uploadProgressNote(List<MultipartFile> progressNoteFiles, String progressNoteId) {
        try {
            ProgressNoteModel progressNote = progressNoteRepository.findById(progressNoteId)
                    .orElseThrow(() -> new AppException(
                            ResponseMessages.PATIENT_NOT_FOUND + " con ID: " + progressNoteId,
                            HttpStatus.NOT_FOUND));

            List<ProgressNoteFileModel> progressNotes = new ArrayList<>();

            for (MultipartFile file : progressNoteFiles) {
                String fileName = fileStorageService.storeFile(file);

                ProgressNoteFileModel newProgressNote = new ProgressNoteFileModel();
                newProgressNote.setUrl(Constants.UPLOAD_PATH + fileName);
                newProgressNote.setExtension(fileStorageService.getFileExtension(file.getOriginalFilename()));
                newProgressNote.setProgressNote(progressNote);
                String fName = fileName.substring(0, fileName.lastIndexOf("."));

                newProgressNote.setIdProgressNoteFile(fName);

                progressNotes.add(newProgressNote);
            }

            progressNoteFileRepository.saveAll(progressNotes);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_CREATING_PROGRESS_NOTE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public Page<ProgressNoteResponse> getProgressNotesByPatient(String patientId, Pageable pageable) {
        try {
            if (patientId == null || patientId.trim().isEmpty()) {
                throw new AppException(ResponseMessages.PATIENT_ID_CANNOT_BE_EMPTY, HttpStatus.BAD_REQUEST);
            }

            PatientModel patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new AppException(
                            ResponseMessages.PATIENT_NOT_FOUND + " con ID: " + patientId,
                            HttpStatus.NOT_FOUND));

            Page<ProgressNoteModel> progressNotes = progressNoteRepository.findByPatient(patient, pageable);

            List<ProgressNoteResponse> progressNoteResponses = progressNotes.stream().map(progressNote -> {
                ProgressNoteResponse progressNoteResponse = progressNoteMapper.toDto(progressNote);
                List<ProgressNoteFileModel> files = progressNoteFileRepository.findByProgressNote(progressNote);

                List<ProgressNoteFileResponse> fileResponses = files.stream()
                        .map(file -> progressNoteFileMapper.toDto(file, progressNote.getCreatedAt()))
                        .collect(Collectors.toList());

                progressNoteResponse.setFiles(fileResponses);

                String professorName = validationUtils.getFullNameFromPerson(progressNote.getProfessor().getPerson());
                String patientName = validationUtils.getFullNameFromPerson(patient.getPerson());

                progressNoteResponse.setStudent(this.getStudent(progressNote.getCreatedBy()));
                progressNoteResponse.setProfessor(professorName);
                progressNoteResponse.getPatient().setName(patientName);

                return progressNoteResponse;
            }).collect(Collectors.toList());

            return new PageImpl<>(progressNoteResponses, pageable, progressNotes.getTotalElements());
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_FETCHING_PROGRESS_NOTES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<byte[]> downloadProgressNoteById(String id) {
        ProgressNoteFileModel fileModel = progressNoteFileRepository.findById(id)
                .orElseThrow(() -> new AppException(ResponseMessages.FILE_NOT_FOUND, HttpStatus.NOT_FOUND));

        try {
            Path filePath = Paths.get(fileModel.getUrl());
            byte[] fileBytes = Files.readAllBytes(filePath);

            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileModel.getUrl() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(filePath))
                    .body(fileBytes);
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_WHILE_DOWNLOAD_FILE, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private String getStudent(String userId) {
        UserResponse user = userService.getUserById(userId);
        String role = user.getRole().getRole().toString();
        String username = user.getUsername();

        if (ERole.ROLE_PROFESSOR.toString().equals(role)) {
            ProfessorModel professor = professorRepository.findById(username)
                    .orElseThrow(() -> new AppException(ResponseMessages.PROFESSOR_NOT_FOUND, HttpStatus.NOT_FOUND));
            return validationUtils.getFullNameFromPerson(professor.getPerson());
        } else if (ERole.ROLE_STUDENT.toString().equals(role)) {
            StudentModel student = studentRepository.findById(username)
                    .orElseThrow(() -> new AppException(ResponseMessages.STUDENT_NOT_FOUND, HttpStatus.NOT_FOUND));
            return validationUtils.getFullNameFromPerson(student.getPerson());
        } else {
            throw new AppException(ResponseMessages.INVALID_ROLE, HttpStatus.BAD_REQUEST);
        }
    }
}
