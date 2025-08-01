package edu.mx.unsis.unsiSmile.service.files;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.response.FileResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.AnswerMapper;
import edu.mx.unsis.unsiSmile.mappers.FileMapper;
import edu.mx.unsis.unsiSmile.model.AnswerModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientMedicalRecordModel;
import edu.mx.unsis.unsiSmile.model.files.FileModel;
import edu.mx.unsis.unsiSmile.model.files.GeneralFileModel;
import edu.mx.unsis.unsiSmile.repository.IAnswerRepository;
import edu.mx.unsis.unsiSmile.repository.files.IFileRepository;
import edu.mx.unsis.unsiSmile.repository.files.IGeneralFileRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IPatientMedicalRecordRepository;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {
    private final IFileRepository fileRepository;
    private final FileMapper fileMapper;
    private final IAnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final IPatientMedicalRecordRepository patientMedicalRecordRepository;
    private final IGeneralFileRepository generalFileRepository;

    public void upload(List<MultipartFile> files, Long idPatientMedicalRecord, Long idQuestion) {
        if (files.isEmpty() || idPatientMedicalRecord == null || idQuestion == null) {
            throw new AppException("Empty file, idQuestion or idPatientMedicalRecord", HttpStatus.BAD_REQUEST);
        }

        this.uploadFile(files, idPatientMedicalRecord, idQuestion);
    }

    private void uploadFile(List<MultipartFile> files, Long idPatientMedicalRecord, Long idQuestion) {
        Long answerId = this.createFromFile(idPatientMedicalRecord, idQuestion);
        processUpload(files, answerId, false);
    }

    public void processUpload(List<MultipartFile> files, Long answerId, boolean isGeneral) {
        ensureUploadDirectoryExists();

        for (MultipartFile file : files) {
            try {
                if (file.getSize() > 5 * 1024 * 1024) {
                    throw new AppException(
                            String.format(ResponseMessages.FILE_SIZE_EXCEEDED, file.getOriginalFilename()),
                            HttpStatus.BAD_REQUEST);
                }

                String originalName = file.getOriginalFilename();
                if (originalName == null) {
                    throw new AppException(ResponseMessages.FILE_NAME_NULL, HttpStatus.BAD_REQUEST);
                }

                String fileId = UUID.randomUUID().toString();
                String ext = originalName.substring(originalName.lastIndexOf('.') + 1);
                Path path = Paths.get(Constants.UPLOAD_PATH + fileId);
                Files.write(path, file.getBytes());

                if (isGeneral) {
                    GeneralFileModel generalFile = GeneralFileModel.builder()
                            .idFile(fileId)
                            .url(path.toString())
                            .name(originalName)
                            .extension(ext)
                            .build();
                    generalFileRepository.save(generalFile);
                } else {
                    FileModel fileModel = FileModel.builder()
                            .idFile(fileId)
                            .filePath(path.toString())
                            .fileName(originalName)
                            .fileType(ext)
                            .answer(AnswerModel.builder().idAnswer(answerId).build())
                            .build();
                    fileRepository.save(fileModel);
                }
            } catch (AppException e) {
                throw e;
            } catch (Exception e) {
                throw new AppException(ResponseMessages.ERROR_WHILE_UPLOAD_FILE, HttpStatus.INTERNAL_SERVER_ERROR, e);
            }
        }
    }

    public void deleteFile(String idFile) {
        FileModel fileModel = fileRepository.findById(idFile)
                .orElseThrow(() -> new AppException("File not found", HttpStatus.NOT_FOUND));

        try {
            Path filePath = Paths.get(fileModel.getFilePath());
            Files.deleteIfExists(filePath); // Eliminar el archivo físico

            fileRepository.delete(fileModel); // Eliminar el registro de la base de datos
        } catch (Exception e) {
            throw new AppException("Error while deleting file", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    public FileModel updateFile(String idFile, MultipartFile newFile) {
        FileModel fileModel = fileRepository.findById(idFile)
                .orElseThrow(() -> new AppException("File not found", HttpStatus.NOT_FOUND));

        try {
            Path oldFilePath = Paths.get(fileModel.getFilePath());
            Files.deleteIfExists(oldFilePath); // Eliminar el archivo antiguo

            byte[] bytes = newFile.getBytes();
            String String = UUID.randomUUID().toString().replace("-", "");
            Path newPath = Paths.get(Constants.UPLOAD_PATH + String);
            String newFileName = newFile.getOriginalFilename();
            if (newFileName == null) {
                throw new AppException("New file name is null", HttpStatus.BAD_REQUEST);
            }
            String ext = newFileName.substring(newFileName.lastIndexOf(".") + 1); // Extraer la nueva extensión
            Files.write(newPath, bytes);

            // Actualizar el modelo con la nueva información
            fileModel.setFilePath(newPath.toString());
            fileModel.setFileName(newFileName);
            fileModel.setFileType(ext);

            return fileRepository.save(fileModel); // Guardar los cambios en la base de datos
        } catch (Exception e) {
            throw new AppException("Error while updating file", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public FileResponse findById(String id) {
        try {
            Assert.notNull(id, "Id cannot be null");

            FileModel fileModel = fileRepository.findById(id)
                    .orElseThrow(() -> new AppException("File not found with id: " + id, HttpStatus.NOT_FOUND));

            return fileMapper.toDto(fileModel);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to find file with id: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<FileResponse> findAll() {
        try {
            List<FileModel> fileModelList = fileRepository.findAll();

            if (fileModelList.isEmpty()) {
                throw new AppException("No files found", HttpStatus.NOT_FOUND);
            } else {
                return fileModelList.stream()
                        .map(fileMapper::toDto)
                        .collect(Collectors.toList());
            }
        } catch (Exception ex) {
            throw new AppException("Failed to fetch files", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<FileResponse> getFilesByAnswer(Long answerId) {
        try {
            List<FileModel> fileModelList = fileRepository.findAllByAnswerId(answerId);

            return fileModelList.stream()
                    .map(fileMapper::toDto)
                    .collect(Collectors.toList());

        } catch (Exception ex) {
            throw new AppException("Failed to fetch files for answer with ID: " + answerId,
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    public ResponseEntity<byte[]> downloadFileById(String id) {
        FileModel fileModel = fileRepository.findById(id)
                .orElseThrow(() -> new AppException("File not found", HttpStatus.NOT_FOUND));
        return buildFileDownloadResponse(fileModel.getFilePath(), fileModel.getFileName());
    }

    private Long createFromFile(Long idPatientMedicalRecord, Long idQuestion) {
        try {
            Optional<AnswerModel> existingAnswer;
            PatientMedicalRecordModel patientMedicalRecordModel = null;

            if (idPatientMedicalRecord != null) {
                patientMedicalRecordModel = patientMedicalRecordRepository.findById(idPatientMedicalRecord)
                        .orElseThrow(
                                () -> new AppException("Patient Clinical History not found", HttpStatus.NOT_FOUND));

                existingAnswer = answerRepository.findByQuestionModelIdQuestionAndPatientModel_IdPatient(
                        idQuestion, patientMedicalRecordModel.getPatient().getIdPatient());
            } else {
                existingAnswer = answerRepository.findByQuestionModel_IdQuestion(idQuestion);
            }

            if (existingAnswer.isPresent()) {
                return existingAnswer.get().getIdAnswer();
            }

            AnswerModel newAnswerModel = answerMapper.toEntityFromFile(patientMedicalRecordModel, idQuestion);

            newAnswerModel = answerRepository.save(newAnswerModel);

            return newAnswerModel.getIdAnswer();
        } catch (Exception ex) {
            throw new AppException("Failed to save answer", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    public ResponseEntity<byte[]> buildFileDownloadResponse(String path, String fileName) {
        try {
            Path filePath = Paths.get(path);
            byte[] fileBytes = Files.readAllBytes(filePath);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(filePath))
                    .body(fileBytes);
        } catch (Exception e) {
            throw new AppException("Error while downloading file", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private void ensureUploadDirectoryExists() {
        Path uploadDir = Paths.get(Constants.UPLOAD_PATH);
        if (!Files.exists(uploadDir)) {
            try {
                Files.createDirectories(uploadDir);
            } catch (Exception e) {
                throw new AppException("Could not create upload directory", HttpStatus.INTERNAL_SERVER_ERROR, e);
            }
        }
    }
}
